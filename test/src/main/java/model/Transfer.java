package model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Objeto de entrada de dados no sistema")
public class Transfer {

    @Schema(description = "Valor da transação", example="2005.0")
    private Double transferValue;

    @Schema(description = "Data para a qual a transação deve ser agendada", example="2025-12-26")
    private LocalDate transferDate;

    public Double getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(Double transferValue) {
        this.transferValue = transferValue;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }
}
