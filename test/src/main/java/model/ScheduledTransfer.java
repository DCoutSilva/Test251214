package model;

import java.util.Date;

public class ScheduledTransfer {
    private Long id;
    private Double transferValue;
    private Double taxValue;
    private TaxType type;
    private Date transferDate;
    private Date requestDate;

    public ScheduledTransfer(Transfer transfer){
        super();
        this.transferDate = transfer.getTransferDate();
        this.transferValue = transfer.getTransferValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(Double transferValue) {
        this.transferValue = transferValue;
    }

    public Double getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(Double taxValue) {
        this.taxValue = taxValue;
    }

    public TaxType getType() {
        return type;
    }

    public void setType(TaxType type) {
        this.type = type;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
}
