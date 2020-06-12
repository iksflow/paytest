package com.iksflow.assignment.web.dto;

import com.iksflow.assignment.domain.payment.PaymentDetail;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PaymentSaveResponseDto {
//    private String tid;
    private String aid;
    private BigDecimal totalAmount;
    private LocalDateTime createdDate;


    @Builder
    public PaymentSaveResponseDto(PaymentDetail entity) {
//        this.tid = entity.getPayment().getTid();
        this.aid = entity.getAid();

//        this.totalAmount = entity.
//        this.totalAmount = entity.get
//        this.cardNumber = cardNumber;
    }
}
