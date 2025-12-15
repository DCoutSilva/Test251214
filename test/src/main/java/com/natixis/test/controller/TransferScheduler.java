package com.natixis.test.controller;

import com.natixis.test.exception.InvalidTransferException;
import com.natixis.test.exception.TransferNotFoundException;
import com.natixis.test.service.SchedulerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.natixis.test.model.ScheduledTransfer;
import com.natixis.test.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Scheduler", description = "Endpoints para agendar transações bancarias")
public class TransferScheduler {

    @Autowired
    private SchedulerService scheduler;

    @GetMapping("/transfers")
    @Operation(summary = "Listar transações agendadas",
            description = "Retorna a lista de transações cadastradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transações encontrada com sucesso")
    })
    public ResponseEntity<List<ScheduledTransfer>> getScheduledTransfers(){
        return ResponseEntity.ok(scheduler.getAllTransfers());
    }

    @GetMapping("/transfer/{id}")
    @Operation(summary = "Encontrar uma transação agendada especifica por id",
            description = "Retorna a transação com o id especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<ScheduledTransfer> getScheduledTransferById(@PathVariable Long id){
        return ResponseEntity.ok(scheduler.getScheduledTransferById(id));
    }

    @PostMapping("/transfer")
    @Operation(summary = "Agendar uma transação no sistema",
            description = "Retorna a transação com a taxa calculada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<ScheduledTransfer> createNewTransfer(@RequestBody Transfer transfer) throws Exception{
        try{
            ScheduledTransfer st = scheduler.scheduleTransfer(transfer);
            return ResponseEntity.status(HttpStatus.CREATED).body(st);
        }catch(InvalidTransferException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/transfer/{id}")
    @Operation(summary = "Alterar uma transação agendada no sistema com o id especificado",
            description = "Retorna a transação com a taxa recalculada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação registrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Transação inválida"),

    })
    public ResponseEntity<ScheduledTransfer> updateTransfer(@RequestBody Transfer transfer, @PathVariable Long id) throws Exception{
        try{
            ScheduledTransfer updated = scheduler.updateTransfer(id, transfer);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updated);
        }catch(InvalidTransferException e){
            return ResponseEntity.badRequest().build();
        }catch(TransferNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/transfer/{id}")
    @Operation(summary = "Apagar uma transação no sistema com o id especificado",
            description = "Retorna uma mensagem informandoo sucesso ou falha da operação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A mesagem de sucesso ou falha é entregue ao usuario")
    })
    public ResponseEntity<String> deleteTransfer(@PathVariable Long id){
        try {
            scheduler.deleteTransfer(id);
            return ResponseEntity.ok(String.format("Transfer %s deleted", id));
        }catch(TransferNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Transfer %s not found", id));
        }

    }
}
