package com.iksflow.assignment.domain.payment;

import com.iksflow.assignment.utils.PaymentUtil;
import com.iksflow.assignment.web.dto.PaymentSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentDetailRepositoryTest {

    @Autowired
    PaymentUtil pu;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentDetailRepository paymentDetailRepository;

    @After
    public void cleanup() {
        paymentDetailRepository.deleteAll();
        paymentRepository.deleteAll();
    }

    @Test
    public void insertPaymentDetail() {
        // given
        PaymentSaveRequestDto requestDto = PaymentSaveRequestDto.builder()
                .cardNumber("1234567890123456")
                .expiryMonthYear("0620")
                .cvcNumber("999")
                .installMonth(0)
                .totalAmount(BigDecimal.valueOf(100000))
                .vatAmount(BigDecimal.ZERO)
                .build();

        // create master data
        paymentRepository.save(Payment.builder()
                .build());
        List<Payment> paymentList = paymentRepository.findAll();
        Payment payment = paymentList.get(0);

        paymentDetailRepository.save(PaymentDetail.builder()
//                .aid(pu.createUniqueId())
                .requestDto(requestDto)
                .payment(payment)
                .build());
        // when
        List<PaymentDetail> paymentDetailList = paymentDetailRepository.findAll();

        // then
        PaymentDetail paymentDetail = paymentDetailList.get(0);
        assertThat(payment.getTid()).isEqualTo(paymentDetail.getPayment().getTid());
    }

    @Test
    public void createPayString() {
        // given
        PaymentSaveRequestDto requestDto = PaymentSaveRequestDto.builder()
                .state("PAYMENT")
                .cardNumber("1234567890123456")
                .expiryMonthYear("0620")
                .cvcNumber("999")
                .installMonth(0)
                .totalAmount(BigDecimal.valueOf(100000))
                .vatAmount(BigDecimal.valueOf(10000))
                .build();
        Payment payment = requestDto.toEntity();
        PaymentDetail paymentDetail = requestDto.toEntity(payment);

        // when
        StringBuffer sb = new StringBuffer();
        int dataMaxLength = 450;
        String dataLengthHeader = String.format("%4s", dataMaxLength-4);
        String dataTypeHeader = String.format("%-10s", requestDto.getState());
        String aidHeader = paymentDetail.getAid();
        String cardNumber = String.format("%-20s", requestDto.getCardNumber());
        String installMonth = String.format("%02d", requestDto.getInstallMonth());
        String expiryMonthYear = requestDto.getExpiryMonthYear();
        String cvcNumber = requestDto.getCvcNumber();
        String totalAmount = String.format("%10s", requestDto.getTotalAmount().toString());
        String vatAmount = String.format("%10s", requestDto.getVatAmount().toString()).replace(" ", "0");
        String tidHeader = (requestDto.getState().equals("PAYMENT")) ? String.format("%20s", "") : String.format("%20s", payment.getTid());
        String encryptCardInfo = String.format("%300s", pu.encrypt(requestDto.getCardNumber() + "|" + requestDto.getExpiryMonthYear() + "|" + requestDto.getCvcNumber()));
        String extraSpace = String.format("%47s", "");
        sb.append(dataLengthHeader)
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

        // then
        assertThat(dataLengthHeader).isEqualTo(" 446");
        assertThat(dataTypeHeader).isEqualTo("PAYMENT   ");
        assertThat(aidHeader).isEqualTo(paymentDetail.getAid());
        assertThat(cardNumber).isEqualTo("1234567890123456    ");
        assertThat(installMonth).isEqualTo("00");
        assertThat(expiryMonthYear).isEqualTo("0620");
        assertThat(cvcNumber).isEqualTo("999");
        assertThat(totalAmount).isEqualTo("    100000");
        assertThat(vatAmount).isEqualTo("0000010000");
        // when state : PAYMENT
        assertThat(tidHeader).isEqualTo(String.format("%20s", ""));
        // when state : CANCEL
//        assertThat(tidHeader).isEqualTo(payment.getTid());
        assertThat(encryptCardInfo.length()).isEqualTo(300);
        assertThat(extraSpace.length()).isEqualTo(47);
        assertThat(sb.toString().length()).isEqualTo(450);

    }
}
