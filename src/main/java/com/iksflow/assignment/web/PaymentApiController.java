package com.iksflow.assignment.web;

import com.iksflow.assignment.domain.payment.PaymentDetailRepository;
import com.iksflow.assignment.service.PaymentService;
import com.iksflow.assignment.web.dto.PaymentCancelRequestDto;
import com.iksflow.assignment.web.dto.PaymentResponseDto;
import com.iksflow.assignment.web.dto.PaymentSaveRequestDto;
import com.iksflow.assignment.web.dto.PaymentSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping("/api/v1/payment")
    public PaymentResponseDto save(@RequestBody PaymentSaveRequestDto requestDto) {
        return paymentService.save(requestDto);
    }

    @PostMapping("/api/v1/payment/cancel")
    public PaymentResponseDto cancel(@RequestBody PaymentCancelRequestDto requestDto) {
        return paymentService.cancel(requestDto);
    }
}
