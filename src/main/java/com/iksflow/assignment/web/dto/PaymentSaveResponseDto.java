package com.iksflow.assignment.web.dto;

import com.iksflow.assignment.domain.payment.PaymentDetail;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PaymentSaveResponseDto {
    private String tid;
    private String aid;
    private BigDecimal totalAmount;


    @Builder
    public PaymentSaveResponseDto(PaymentDetail entity) {
//        this.tid = entity.getPayment().getTid();
        this.aid = entity.getAid();
//        this.totalAmount = entity.get
//        this.cardNumber = cardNumber;
    }
}
