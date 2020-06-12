package com.iksflow.assignment.web.dto;

import com.iksflow.assignment.domain.payment.Payment;
import com.iksflow.assignment.domain.payment.PaymentDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentCancelRequestDto {
    private String aid;
    private BigDecimal cancelTotalAmount;
    private BigDecimal cancelVatAmount;

    @Builder
    public PaymentCancelRequestDto(String aid, BigDecimal cancelTotalAmount, BigDecimal cancelVatAmount) {
        this.aid = aid;
        this.cancelTotalAmount = cancelTotalAmount;
        this.cancelVatAmount = cancelVatAmount;
    }
}
