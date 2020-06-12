package com.iksflow.assignment.web;

import com.iksflow.assignment.domain.payment.Payment;
import com.iksflow.assignment.domain.payment.PaymentDetail;
import com.iksflow.assignment.domain.payment.PaymentDetailRepository;
import com.iksflow.assignment.domain.payment.PaymentRepository;
import com.iksflow.assignment.web.dto.PaymentResponseDto;
import com.iksflow.assignment.web.dto.PaymentSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @After
    public void tearDown() throws Exception {
        paymentDetailRepository.deleteAll();
        paymentRepository.deleteAll();
    }

    @Test
    public void test_결제API() throws Exception {
        // given
        String cardNumber = "1234567890123456";
        String expiryMonthYear = "0620";
        String cvcNumber = "999";
        int installMonth = 0;
        BigDecimal totalAmount = BigDecimal.valueOf(100000);
        BigDecimal vatAmount = BigDecimal.ZERO;

        PaymentSaveRequestDto requestDto = PaymentSaveRequestDto.builder()
                .cardNumber(cardNumber)
                .expiryMonthYear(expiryMonthYear)
                .cvcNumber(cvcNumber)
                .installMonth(installMonth)
                .totalAmount(totalAmount)
                .vatAmount(vatAmount)
                .build();

        String url = "http://localhost:" + port + "/api/v1/payment";

        // when
        ResponseEntity<PaymentResponseDto> responseEntity = restTemplate.postForEntity(url, requestDto, PaymentResponseDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getTid().length()).isEqualTo(20);

        List<PaymentDetail> all = paymentDetailRepository.findAll();
        PaymentDetail paymentDetail = all.get(0);

        // response>required 관리번호(aid) : payment_detail.aid와 response.aid 비교
        assertThat(paymentDetail.getAid()).isEqualTo(responseEntity.getBody().getAid());
    }


    @Test
    public void temp() {
//        System.out.println(new BigDecimal("100000").divide(new BigDecimal("11")).toString());
        System.out.println("<<TEST START>>");
        System.out.println(new BigDecimal("11000"));
        System.out.println(new BigDecimal("11000").divide(new BigDecimal("11"), 0, BigDecimal.ROUND_HALF_UP));
        System.out.println("<<TEST END>>");
    }
}
