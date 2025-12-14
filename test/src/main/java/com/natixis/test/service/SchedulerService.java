package com.natixis.test.service;

import com.natixis.test.exception.InvalidTransferException;
import com.natixis.test.exception.TransferNotFoundException;
import model.ScheduledTransfer;
import model.Transfer;

import java.util.List;

public interface SchedulerService {

    List<ScheduledTransfer> getAllTransfers();

    ScheduledTransfer scheduleTransfer(Transfer transfer) throws InvalidTransferException;

    ScheduledTransfer updateTransfer(Long id, Transfer transfer)throws TransferNotFoundException, InvalidTransferException;

    ScheduledTransfer getScheduledTransferById(Long id) throws TransferNotFoundException;

    void deleteTransfer(Long id) throws TransferNotFoundException;
}
