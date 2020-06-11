package com.iksflow.assignment.web.dto;

import com.iksflow.assignment.domain.payment.Payment;
import com.iksflow.assignment.domain.payment.PaymentDetail;
import com.iksflow.assignment.utils.PaymentUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentSaveRequestDto {

    private String state;
    private String cardNumber;
    private String expiryMonthYear;
    private String cvcNumber;
    private int installMonth;
    private BigDecimal totalAmount;
    private BigDecimal vatAmount;
    private String encryptedCardInfo;

    @Builder
    public PaymentSaveRequestDto(String state, String cardNumber, String expiryMonthYear, String cvcNumber,
                                 int installMonth, BigDecimal totalAmount, BigDecimal vatAmount) {
        this.state = "PAYMENT";
        this.cardNumber = cardNumber;
        this.expiryMonthYear = expiryMonthYear;
        this.cvcNumber = cvcNumber;
        this.installMonth = installMonth;
        this.totalAmount = totalAmount;
        this.vatAmount = vatAmount;
    }

    public void setEncryptedCardInfo(String encryptedCardInfo) {
        this.encryptedCardInfo = encryptedCardInfo;
    }

    public Payment toEntity() {
        return Payment.builder()
                .build();
    }

    public PaymentDetail toEntity(Payment payment) {
        return PaymentDetail.builder()
                .requestDto(this)
                .payment(payment)
                .build();
    }
}
