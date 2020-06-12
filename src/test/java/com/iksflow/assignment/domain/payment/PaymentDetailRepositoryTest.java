package com.iksflow.assignment.domain.payment;

import com.iksflow.assignment.utils.PaymentUtil;
import com.iksflow.assignment.web.dto.PaymentResponseDto;
import com.iksflow.assignment.web.dto.PaymentSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

//    @Test
//    public void insertPaymentDetail() {
//        // given
//        PaymentSaveRequestDto requestDto = PaymentSaveRequestDto.builder()
//                .cardNumber("1234567890123456")
//                .expiryMonthYear("0620")
//                .cvcNumber("999")
//                .installMonth(0)
//                .totalAmount(new BigDecimal("100000"))
//                .vatAmount(BigDecimal.ZERO)
//                .build();
//
//        // create master data
//        paymentRepository.save(Payment.builder()
//                .build());
//        List<Payment> paymentList = paymentRepository.findAll();
//        Payment payment = paymentList.get(0);
//
//        paymentDetailRepository.save(PaymentDetail.builder()
////                .aid(pu.createUniqueId())
//                .requestDto(requestDto)
//                .payment(payment)
//                .build());
//        // when
//        List<PaymentDetail> paymentDetailList = paymentDetailRepository.findAll();
//
//        // then
//        PaymentDetail paymentDetail = paymentDetailList.get(0);
//        assertThat(payment.getTid()).isEqualTo(paymentDetail.getPayment().getTid());
//    }

    @Test
    public void test_string데이터생성() {
        // given
        PaymentSaveRequestDto requestDto = PaymentSaveRequestDto.builder()
                .cardNumber("1234567890123456")
                .expiryMonthYear("0620")
                .cvcNumber("999")
                .installMonth(0)
                .totalAmount(new BigDecimal("100000"))
                .vatAmount(new BigDecimal("10000"))
                .build();
        Payment payment = requestDto.toEntity();
        PaymentDetail paymentDetail = requestDto.toEntity(payment);

        // when
        String payString = paymentDetail.getPayString();

        // then
        assertThat(payString.substring(0, 4)).isEqualTo(" 446");
        assertThat(payString.substring(4, 14)).isEqualTo("PAYMENT   ");
        assertThat(payString.substring(14, 34)).isEqualTo(paymentDetail.getAid());
        assertThat(payString.substring(34, 54)).isEqualTo("1234567890123456    ");
        assertThat(payString.substring(54, 56)).isEqualTo("00");
        assertThat(payString.substring(56, 60)).isEqualTo("0620");
        assertThat(payString.substring(60, 63)).isEqualTo("999");
        assertThat(payString.substring(63, 73)).isEqualTo("    100000");
        assertThat(payString.substring(73, 83)).isEqualTo("0000010000");
//        // when state : PAYMENT
        assertThat(payString.substring(83, 103)).isEqualTo(String.format("%20s", ""));
//        // when state : CANCEL
//        assertThat(payString.substring(83, 103)).isEqualTo(payment.getTid());
        assertThat(payString.substring(103, 403).length()).isEqualTo(300);
        assertThat(payString.substring(403, 450).length()).isEqualTo(47);
        assertThat(payString.length()).isEqualTo(450);

    }

    @Test
    public void test_string데이터파싱() {
        // given
        PaymentSaveRequestDto requestDto = PaymentSaveRequestDto.builder()
                .cardNumber("1234567890123456")
                .expiryMonthYear("0620")
                .cvcNumber("999")
                .installMonth(0)
                .totalAmount(new BigDecimal("100000"))
                .vatAmount(new BigDecimal("10000"))
                .build();
        requestDto.setEncryptedCardInfo(pu.encrypt("1234567890123456" + "|" + "0620" + "|" + "999"));
        Payment payment = requestDto.toEntity();
        PaymentDetail paymentDetail = requestDto.toEntity(payment);
        String payString = paymentDetail.getPayString();

        // when
        PaymentResponseDto responseDto = new PaymentResponseDto(paymentDetail);
        String[] cardInfoArray = pu.decrypt(responseDto.getEncryptedCardInfo()).split("\\|");
        responseDto.setCardDataFromCardInfoArray(cardInfoArray);

        // then
        assertThat(responseDto.getTid()).isEqualTo(paymentDetail.getPayment().getTid());
        assertThat(responseDto.getAid()).isEqualTo(paymentDetail.getAid());
        assertThat(responseDto.getState()).isEqualTo("PAYMENT");
        assertThat(responseDto.getCardNumber()).isEqualTo("1234567890123456");
        assertThat(responseDto.getExpiryMonthYear()).isEqualTo("0620");
        assertThat(responseDto.getCvcNumber()).isEqualTo("999");
        assertThat(responseDto.getTotalAmount()).isEqualTo(new BigDecimal("100000"));
        assertThat(responseDto.getVatAmount()).isEqualTo(new BigDecimal("10000"));
        assertThat(responseDto.getCreatedDate()).isEqualTo(paymentDetail.getCreatedDate());
    }


}
