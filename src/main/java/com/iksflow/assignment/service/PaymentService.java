package com.iksflow.assignment.service;

import com.iksflow.assignment.domain.payment.Payment;
import com.iksflow.assignment.domain.payment.PaymentDetail;
import com.iksflow.assignment.domain.payment.PaymentDetailRepository;
import com.iksflow.assignment.domain.payment.PaymentRepository;
import com.iksflow.assignment.utils.PaymentUtil;
import com.iksflow.assignment.web.dto.PaymentResponseDto;
import com.iksflow.assignment.web.dto.PaymentSaveRequestDto;
import com.iksflow.assignment.web.dto.PaymentSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Service
public class PaymentService {

    @Autowired
    PaymentUtil pu;

    private final PaymentRepository paymentRepository;
    private final PaymentDetailRepository paymentDetailRepository;

    public PaymentSaveResponseDto save(PaymentSaveRequestDto requestDto) {
        String encryptedCardInfo = pu.encrypt(requestDto.getCardNumber() + "|" + requestDto.getExpiryMonthYear() + "|" + requestDto.getCvcNumber());
        requestDto.setEncryptedCardInfo(encryptedCardInfo);
        Payment payment = requestDto.toEntity();
        PaymentDetail paymentDetail = requestDto.toEntity(payment);
        paymentRepository.save(payment).getTid();
        paymentDetailRepository.save(paymentDetail);
        return new PaymentSaveResponseDto(paymentDetail);
    }
}
