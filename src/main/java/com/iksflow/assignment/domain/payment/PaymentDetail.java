package com.iksflow.assignment.domain.payment;

import com.iksflow.assignment.domain.BaseTimeEntity;
import com.iksflow.assignment.web.dto.PaymentSaveRequestDto;
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

    @Column(name = "payment_detail_paystring", length = 450, nullable = false)
    private String payString;

    @ManyToOne
    @JoinColumn(name = "payment_tid")
    private Payment payment;

    @Builder
    public PaymentDetail(PaymentSaveRequestDto requestDto, Payment payment) {
        this.aid = createUniqueId();
        this.payString = createPayStringFromDto(requestDto);
        this.payment = payment;
    }
//    public PaymentDetail(String cardNumber, String expiryMonthYear, String cvcNumber, String installMonth,
//                         BigDecimal totalAmount, BigDecimal vatAmount, Payment payment) {
//        this.aid = createUniqueId();
//        this.payString = createPayStringFromDto();
//        this.payment = payment;
//    }

    private String createUniqueId() {
        int random = Double.valueOf(Math.random() * 30).intValue();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String smallUuid = uuid.substring(random, random + 3);
        String timeId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String uniqueId = timeId + smallUuid;

        return uniqueId;
    }

    public String createPayStringFromDto(PaymentSaveRequestDto requestDto) {
        StringBuffer payStrBuffer = new StringBuffer();
        int dataMaxLength = 450;
        String dataLengthHeader = String.format("%4s", dataMaxLength-4);
        String dataTypeHeader = String.format("%-10s", requestDto.getState());
        String aidHeader = this.getAid();
        String cardNumber = String.format("%-20s", requestDto.getCardNumber());
        String installMonth = String.format("%02d", requestDto.getInstallMonth());
        String expiryMonthYear = requestDto.getExpiryMonthYear();
        String cvcNumber = requestDto.getCvcNumber();
        String totalAmount = String.format("%10s", requestDto.getTotalAmount().toString());
        String vatAmount = String.format("%10s", requestDto.getVatAmount().toString()).replace(" ", "0");
        String tidHeader = (requestDto.getState().equals("PAYMENT")) ? String.format("%20s", "") : String.format("%20s", payment.getTid());
        String encryptCardInfo = String.format("%300s", requestDto.getEncryptedCardInfo());
        String extraSpace = String.format("%47s", "");
        payStrBuffer.append(dataLengthHeader)
                .append(dataTypeHeader)
                .append(aidHeader)
                .append(cardNumber)
                .append(installMonth)
                .append(expiryMonthYear)
                .append(cvcNumber)
                .append(totalAmount)
                .append(vatAmount)
                .append(tidHeader)
                .append(encryptCardInfo)
                .append(extraSpace);

        return payStrBuffer.toString();
    }
}
