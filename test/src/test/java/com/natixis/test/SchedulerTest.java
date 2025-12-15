package com.natixis.test;

import com.natixis.test.controller.TransferScheduler;
import com.natixis.test.exception.InvalidTransferException;
import com.natixis.test.exception.TransferNotFoundException;
import com.natixis.test.model.TaxType;
import com.natixis.test.service.SchedulerService;
import com.natixis.test.model.ScheduledTransfer;
import com.natixis.test.model.Transfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do TransferScheduler Controller")
class SchedulerTest {

    @Mock
    private SchedulerService schedulerService;

    @InjectMocks
    private TransferScheduler transferScheduler;

    private ScheduledTransfer scheduledTransfer;
    private Transfer transfer;

    @BeforeEach
    void setUp() {
        // Preparar dados de teste
        transfer = new Transfer();
        transfer.setTransferDate(LocalDate.now().plusDays(11));
        transfer.setTransferValue(2005.0);

        scheduledTransfer = new ScheduledTransfer(transfer);
        scheduledTransfer.setId(0L);
        scheduledTransfer.setRequestDate(LocalDate.now());
        scheduledTransfer.setTaxValue(164.41);
        scheduledTransfer.setType(TaxType.C);
    }

    @Test
    @DisplayName("Deve listar todas as transferências agendadas")
    void testGetScheduledTransfers() {
        // Arrange
        List<ScheduledTransfer> expectedList = Arrays.asList(scheduledTransfer);
        when(schedulerService.getAllTransfers()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<ScheduledTransfer>> response = transferScheduler.getScheduledTransfers();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(scheduledTransfer.getId(), response.getBody().get(0).getId());
        verify(schedulerService, times(1)).getAllTransfers();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver transferências")
    void testGetScheduledTransfersEmpty() {
        // Arrange
        when(schedulerService.getAllTransfers()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<ScheduledTransfer>> response = transferScheduler.getScheduledTransfers();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(schedulerService, times(1)).getAllTransfers();
    }

    @Test
    @DisplayName("Deve buscar transferência por ID com sucesso")
    void testGetScheduledTransferById() {
        // Arrange
        Long id = 0L;
        when(schedulerService.getScheduledTransferById(id)).thenReturn(scheduledTransfer);

        // Act
        ResponseEntity<ScheduledTransfer> result = transferScheduler.getScheduledTransferById(id);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(id, result.getBody().getId());
        assertEquals(scheduledTransfer.getTaxValue(), result.getBody().getTaxValue());
        verify(schedulerService, times(1)).getScheduledTransferById(id);
    }

    @Test
    @DisplayName("Deve criar nova transferência com sucesso")
    void testCreateNewTransfer() throws Exception {
        // Arrange
        when(schedulerService.scheduleTransfer(any(Transfer.class))).thenReturn(scheduledTransfer);

        // Act
        ResponseEntity<ScheduledTransfer> result = transferScheduler.createNewTransfer(transfer);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(scheduledTransfer.getId(), result.getBody().getId());
        verify(schedulerService, times(1)).scheduleTransfer(any(Transfer.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar transferência inválida")
    void testCreateNewTransferInvalid() throws Exception {
        // Arrange
        when(schedulerService.scheduleTransfer(any(Transfer.class)))
                .thenThrow(new InvalidTransferException("Data inválida"));

        // Act
        ResponseEntity<ScheduledTransfer> response = transferScheduler.createNewTransfer(transfer);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(schedulerService, times(1)).scheduleTransfer(any(Transfer.class));
    }

    @Test
    @DisplayName("Deve atualizar transferência com sucesso")
    void testUpdateTransfer() throws Exception {
        // Arrange
        Long id = 0L;
        scheduledTransfer.setTransferValue(2010.0);
        when(schedulerService.updateTransfer(eq(id), any(Transfer.class)))
                .thenReturn(scheduledTransfer);

        // Act
        ResponseEntity<ScheduledTransfer> result = transferScheduler.updateTransfer(transfer, id);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEquals(2010, result.getBody().getTransferValue());
        verify(schedulerService, times(1)).updateTransfer(eq(id), any(Transfer.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar transferência inválida")
    void testUpdateTransferInvalid() throws Exception {
        // Arrange
        Long id = 999L;
        when(schedulerService.updateTransfer(eq(id), any(Transfer.class)))
                .thenThrow(new TransferNotFoundException("Transferência não encontrada"));

        // Act
        ResponseEntity<ScheduledTransfer> response = transferScheduler.updateTransfer(transfer, id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(schedulerService, times(1)).updateTransfer(eq(id), any(Transfer.class));
    }

    @Test
    @DisplayName("Deve deletar transferência com sucesso")
    void testDeleteTransferSuccess() {
        // Arrange
        Long id = 0L;
        doNothing().when(schedulerService).deleteTransfer(id);

        ResponseEntity<String> response = transferScheduler.deleteTransfer(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Transfer 0 deleted", response.getBody());
        verify(schedulerService, times(1)).deleteTransfer(id);
    }

    @Test
    @DisplayName("Deve retornar 404 NOT FOUND ao deletar transferência inexistente")
    void testDeleteTransferNotFound() {
        // Arrange
        Long id = 999L;
        doThrow(new TransferNotFoundException("Not found")).when(schedulerService).deleteTransfer(id);

        // Act
        ResponseEntity<String> response = transferScheduler.deleteTransfer(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Transfer 999 not found", response.getBody());
        verify(schedulerService, times(1)).deleteTransfer(id);
    }
}
