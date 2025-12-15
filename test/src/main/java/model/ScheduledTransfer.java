package model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Representação de uma transação agendada")
public class ScheduledTransfer {

    @Schema(description = "Identificador único de uma transação", example="0")
    private Long id;

    @Schema(description = "Valor da transação solicitado, sem a taxa", example="2005.0")
    private Double transferValue;

    @Schema(description = "Valor calculado para a taxa", example="164.41")
    private Double taxValue;

    @Schema(description = "Tipo de tarifa aplicado", example="C")
    private TaxType type;

    @Schema(description = "Data para a qual a transação foi agendada", example="2025-12-26")
    private LocalDate transferDate;

    @Schema(description = "Data em que foi solicitado o agendamento", example="2025-12-15")
    private LocalDate requestDate;

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

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
}
