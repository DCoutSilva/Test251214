package com.natixis.test.service;

import com.natixis.test.exception.InvalidTransferException;
import com.natixis.test.exception.TransferNotFoundException;
import com.natixis.test.model.ScheduledTransfer;
import com.natixis.test.model.TaxType;
import com.natixis.test.model.Transfer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private List<ScheduledTransfer> allTransfers = new ArrayList<>();
    private Long transferId = 0L;

    @Override
    public List<ScheduledTransfer> getAllTransfers() {
        return allTransfers;
    }

    @Override
    public ScheduledTransfer scheduleTransfer(Transfer transfer) throws InvalidTransferException {
        ScheduledTransfer scheduled = calculateTax(transfer);
        scheduled.setId(transferId++);
        allTransfers.add(scheduled);
        return scheduled;
    }


    @Override
    public ScheduledTransfer updateTransfer(Long id, Transfer transfer) throws TransferNotFoundException, InvalidTransferException {
        ScheduledTransfer scheduled = allTransfers.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        if (scheduled == null) throw new TransferNotFoundException(String.format("Operação id %s não encontrada", id));
        ScheduledTransfer newTransfer = calculateTax(transfer);
        allTransfers.remove(scheduled);
        newTransfer.setId(id);
        allTransfers.add(newTransfer);
        return newTransfer;
    }

    @Override
    public ScheduledTransfer getScheduledTransferById(Long id) throws TransferNotFoundException {
        ScheduledTransfer scheduled = allTransfers.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        if (scheduled == null) throw new TransferNotFoundException(String.format("Operação id %s não encontrada", id));
        return scheduled;
    }

    @Override
    public void deleteTransfer(Long id) throws TransferNotFoundException {
        ScheduledTransfer scheduled = allTransfers.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        if (scheduled == null) throw new TransferNotFoundException(String.format("Operação id %s não encontrada", id));
        allTransfers.remove(scheduled);
    }

    private ScheduledTransfer calculateTax(Transfer transfer) {
        Double value = transfer.getTransferValue();
        ScheduledTransfer result = new ScheduledTransfer(transfer);
        result.setRequestDate(LocalDate.now());
        long dateInterval = ChronoUnit.DAYS.between(LocalDate.now(), transfer.getTransferDate());
        if(value <=0.0){
            throw new InvalidTransferException("Valor de transação inválido");
        }else if(value > 0.0 && value <1001.0){
            result.setType(TaxType.A);
            result.setTaxValue((value * 0.03) + 3);
        }else if(value>=1001.0 && value<2001.0){
            if(dateInterval<=10) {
                result.setType(TaxType.B);
                result.setTaxValue(value * 0.09);
            }
        }else {
            result.setType(TaxType.C);
            if(dateInterval>= 11 && dateInterval <=20){
                result.setTaxValue(value * 0.082);
            } else if(dateInterval>= 21 && dateInterval <=30){
                result.setTaxValue(value * 0.069);
            }else if(dateInterval>= 31 && dateInterval <=40){
                result.setTaxValue(value * 0.047);
            }else if(dateInterval > 40){
                result.setTaxValue(value * 0.017);
            }
        }

        if (result.getTaxValue() == null){
            throw new InvalidTransferException("Valor e data da transação incompativeis");
        }

        return result;
    }
}
