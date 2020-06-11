package com.iksflow.assignment.domain.payment;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentRepositoryTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentDetailRepository paymentDetailRepository;


    @After
    public void cleanup() {
        paymentRepository.deleteAll();
        paymentDetailRepository.deleteAll();
    }

    @Test
    public void insertPayment() {
        // given
        String tid = "abc";

        paymentRepository.save(Payment.builder()
                .build());
        // when
        List<Payment> paymentList = paymentRepository.findAll();

        // then
        Payment payment = paymentList.get(0);
        assertThat(payment.getTid().length()).isEqualTo(20);
    }

}
