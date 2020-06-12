package com.iksflow.assignment.domain.payment;

import com.iksflow.assignment.domain.BaseTimeEntity;
import com.iksflow.assignment.web.dto.PaymentResponseDto;
import com.iksflow.assignment.web.dto.PaymentSaveRequestDto;
import com.iksflow.assignment.web.dto.PaymentSaveResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Table(name = "payment_detail")
@Entity
public class PaymentDetail extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_detail_id")
    private Long id;

    @Column(name = "payment_detail_aid", length = 20, nullable = false)
    private String aid;

    @Column(name = "payment_detail_pay_aid", length = 20)
    private String payAid;

    @Column(name = "payment_detail_state", length = 10)
    private String state;

    @Column(name = "payment_detail_paystring", length = 450, nullable = false)
    private String payString;

    @ManyToOne
    @JoinColumn(name = "payment_tid")
    private Payment payment;

    @Builder
    public PaymentDetail(String payAid, String state, String encryptedCardInfo, String cardNumber, String expiryMonthYear, String cvcNumber,
                         int installMonth, BigDecimal totalAmount, BigDecimal vatAmount, Payment payment) {
        this.aid = createUniqueId();
        this.payString = createPayStringFromDto(state, encryptedCardInfo, cardNumber, expiryMonthYear, cvcNumber, installMonth, totalAmount, vatAmount);
        this.payment = payment;
        this.payAid = payAid;
        this.state = state;
    }

    private String createUniqueId() {
        int random = Double.valueOf(Math.random() * 30).intValue();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String smallUuid = uuid.substring(random, random + 3);
        String timeId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String uniqueId = timeId + smallUuid;

        return uniqueId;
    }

    private String createPayStringFromDto(String state, String encryptedCardInfo, String cardNumber, String expiryMonthYear, String cvcNumber,
                                          int installMonth, BigDecimal totalAmount, BigDecimal vatAmount) {

        StringBuffer payStrBuffer = new StringBuffer();
        int dataMaxLength = 450;
        String dataLengthHeader = String.format("%4s", dataMaxLength-4);
        String dataTypeHeader = String.format("%-10s", state);
        String aidHeaderHeader = this.aid;
        String cardNumberPart = String.format("%-20s", cardNumber);
        String installMonthPart = String.format("%02d", installMonth);
        String expiryMonthYearPart = expiryMonthYear;
        String cvcNumberPart = cvcNumber;
        String totalAmountPart = String.format("%10s", totalAmount.toString());
        String vatAmountPart = (vatAmount != null) ? String.format("%10s", vatAmount.toString()).replace(" ", "0")
                : String.format("%10s", totalAmount.divide(new BigDecimal("11"), 0, BigDecimal.ROUND_HALF_UP).toString()).replace(" ", "0");
        String payAidPart = (state.equals("PAYMENT")) ? String.format("%20s", "") : String.format("%20s", this.payAid);
        String encryptCardInfo = String.format("%300s", encryptedCardInfo);
        String extraSpace = String.format("%47s", "");
        payStrBuffer.append(dataLengthHeader)
                .append(dataTypeHeader)
                .append(aidHeaderHeader)
                .append(cardNumberPart)
                .append(installMonthPart)
                .append(expiryMonthYearPart)
                .append(cvcNumberPart)
                .append(totalAmountPart)
                .append(vatAmountPart)
                .append(payAidPart)
                .append(encryptCardInfo)
                .append(extraSpace);

        return payStrBuffer.toString();
    }

}
