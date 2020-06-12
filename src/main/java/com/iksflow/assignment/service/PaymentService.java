package com.iksflow.assignment.service;

import com.iksflow.assignment.domain.payment.Payment;
import com.iksflow.assignment.domain.payment.PaymentDetail;
import com.iksflow.assignment.domain.payment.PaymentDetailRepository;
import com.iksflow.assignment.domain.payment.PaymentRepository;
import com.iksflow.assignment.utils.PaymentUtil;
import com.iksflow.assignment.web.dto.PaymentCancelRequestDto;
import com.iksflow.assignment.web.dto.PaymentResponseDto;
import com.iksflow.assignment.web.dto.PaymentSaveRequestDto;
import com.iksflow.assignment.web.dto.PaymentSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentService {

    @Autowired
    PaymentUtil pu;

    private final PaymentRepository paymentRepository;
    private final PaymentDetailRepository paymentDetailRepository;

    @Transactional
    public PaymentResponseDto save(PaymentSaveRequestDto requestDto) {
        String encryptedCardInfo = pu.encrypt(requestDto.getCardNumber() + "|" + requestDto.getExpiryMonthYear() + "|" + requestDto.getCvcNumber());
        requestDto.setEncryptedCardInfo(encryptedCardInfo);
        Payment payment = requestDto.toEntity();
        PaymentDetail paymentDetail = requestDto.toEntity(payment);
        paymentRepository.save(payment).getTid();
        paymentDetailRepository.save(paymentDetail);
        PaymentResponseDto responseDto = new PaymentResponseDto(paymentDetail);
        responseDto.setCardDataFromCardInfoArray(pu.decrypt(responseDto.getEncryptedCardInfo()).split("\\|"));

        return responseDto;
    }

    @Transactional
    public PaymentResponseDto cancel(PaymentCancelRequestDto requestDto) {
        PaymentDetail cancelTarget = paymentDetailRepository.findOneByAid(requestDto.getAid());
        PaymentSaveRequestDto saveRequestDto = PaymentSaveRequestDto.builder()
                .state("CANCEL")
                .
//        cancelTarget.getPayment().getTid();
//        paymentRepository.
//        cancelTarget.
    }
}
