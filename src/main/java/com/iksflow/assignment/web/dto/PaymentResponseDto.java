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
public class PaymentResponseDto {
    private String tid;
    private String aid;
    private String state;
    private String cardNumber;
    private String expiryMonthYear;
    private String cvcNumber;
    private BigDecimal totalAmount;
    private BigDecimal vatAmount;
    private String encryptedCardInfo;
    private LocalDateTime createdDate;

    public PaymentResponseDto(PaymentDetail entity) {
        this.tid = entity.getPayment().getTid();
        this.aid = entity.getAid();
        this.state = entity.getState();
        this.createdDate = entity.getCreatedDate();
        this.encryptedCardInfo = entity.getPayString().substring(103, 403).replace(" ", "");
        setAmountFromPayString(entity.getPayString());
    }

    public void setCardDataFromCardInfoArray(String[] cardInfoArray) {
        this.cardNumber = cardInfoArray[0];
        this.expiryMonthYear = cardInfoArray[1];
        this.cvcNumber = cardInfoArray[2];
    }

    private void setAmountFromPayString(String payString) {
        this.totalAmount = new BigDecimal(payString.substring(63, 73).replace(" ", ""));
        this.vatAmount = new BigDecimal(payString.substring(73, 83));
    }
}
