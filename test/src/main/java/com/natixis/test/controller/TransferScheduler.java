package com.natixis.test.controller;

import com.natixis.test.exception.InvalidTransferException;
import com.natixis.test.service.SchedulerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.ScheduledTransfer;
import model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ScheduledTransfer> getScheduledTransfers(){
        return scheduler.getAllTransfers();
    }

    @GetMapping("/transfer/{id}")
    @Operation(summary = "Encontrar uma transação agendada especifica por id",
            description = "Retorna a transação com o id especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro (TODO: fazer o 404 corretamente)")
    })
    public ScheduledTransfer getScheduledTransferById(@PathVariable Long id){
        return scheduler.getScheduledTransferById(id);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Agendar uma transação no sistema",
            description = "Retorna a transação com a taxa calculada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação registrada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro - ver logs")
    })
    public ScheduledTransfer createNewTransfer(@RequestBody Transfer transfer) throws Exception{
        try{
            return scheduler.scheduleTransfer(transfer);
        }catch(InvalidTransferException e){
            throw(new Exception("Invalid transfer", e));
        }
    }

    @PatchMapping("/transfer/{id}")
    @Operation(summary = "Alterar uma transação agendada no sistema com o id especificado",
            description = "Retorna a transação com a taxa recalculada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação registrada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro - ver logs")
    })
    public ScheduledTransfer updateTransfer(@RequestBody Transfer transfer, @PathVariable Long id) throws Exception{
        try{
            return scheduler.updateTransfer(id,transfer);
        }catch(InvalidTransferException e){
            throw(new Exception("Invalid transfer", e));
        }
    }

    @DeleteMapping("/transfer/{id}")
    @Operation(summary = "Apagar uma transação no sistema com o id especificado",
            description = "Retorna uma mensagem informandoo sucesso ou falha da operação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A mesagem de sucesso ou falha é entregue ao usuario")
    })
    public String deleteTransfer(@PathVariable Long id){
        try{
            scheduler.deleteTransfer(id);
            return String.format("Transfer %s deleted", id);
        }catch(Exception e){

            return String.format("Transfer %s not found", id);
        }

    }
}
