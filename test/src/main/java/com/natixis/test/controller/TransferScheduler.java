package com.natixis.test.controller;

import com.natixis.test.exception.InvalidTransferException;
import com.natixis.test.service.SchedulerService;
import model.ScheduledTransfer;
import model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransferScheduler {

    @Autowired
    private SchedulerService scheduler;

    @GetMapping("/transfers")
    public List<ScheduledTransfer> getScheduledTransfers(){
        return scheduler.getAllTransfers();
    }

    @GetMapping("/transfer/{id}")
    public ScheduledTransfer getScheduledTransferById(@PathVariable Long id){
        return scheduler.getScheduledTransferById(id);
    }

    @PostMapping("/transfer")
    public ScheduledTransfer createNewTransfer(@RequestBody Transfer transfer) throws Exception{
        try{
            return scheduler.scheduleTransfer(transfer);
        }catch(InvalidTransferException e){
            throw(new Exception("Invalid transfer", e));
        }
    }

    @PatchMapping("/transfer/{id}")
    public ScheduledTransfer updateTransfer(@RequestBody Transfer transfer, @PathVariable Long id) throws Exception{
        try{
            return scheduler.updateTransfer(id,transfer);
        }catch(InvalidTransferException e){
            throw(new Exception("Invalid transfer", e));
        }
    }

    @DeleteMapping("/transfer/{id}")
    public String deleteTransfer(@PathVariable Long id){
        try{
            scheduler.deleteTransfer(id);
            return String.format("Transfer %s deleted", id);
        }catch(Exception e){

            return String.format("Transfer %s not found", id);
        }

    }
}
