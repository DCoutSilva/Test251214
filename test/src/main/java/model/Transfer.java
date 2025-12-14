package model;

import java.util.Date;

public class Transfer {
    private Double transferValue;
    private Date transferDate;

    public Double getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(Double transferValue) {
        this.transferValue = transferValue;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }
}
