package com.iksflow.assignment.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PaymentResponseDto {
    private String tid;
    private String aid;
    private String cardNumber;
    private String expiryMonthYear;
    private String cvcNumber;
    private String installMonth;
    private BigDecimal totalAmount;
    private BigDecimal vatAmount;

    @Builder
    public PaymentResponseDto(String tid, String aid, String cardNumber, String expiryMonthYear,
                              String cvcNumber, String installMonth, BigDecimal totalAmount, BigDecimal vatAmount) {
        this.tid = tid;
        this.aid = aid;
        this.cardNumber = cardNumber;
        this.expiryMonthYear = expiryMonthYear;
        this.cvcNumber = cvcNumber;
        this.installMonth = installMonth;
        this.totalAmount = totalAmount;
        this.vatAmount = vatAmount;
    }
}
