package com.iksflow.assignment.web;

import com.iksflow.assignment.domain.payment.PaymentDetailRepository;
import com.iksflow.assignment.service.PaymentService;
import com.iksflow.assignment.web.dto.PaymentResponseDto;
import com.iksflow.assignment.web.dto.PaymentSaveRequestDto;
import com.iksflow.assignment.web.dto.PaymentSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping("/api/v1/payment")
    public PaymentSaveResponseDto save(@RequestBody PaymentSaveRequestDto requestDto) {
        return paymentService.save(requestDto);
    }
}
