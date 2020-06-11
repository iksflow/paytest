package com.iksflow.assignment.utils;

import com.iksflow.assignment.config.JasyptConfig;
import com.iksflow.assignment.web.dto.PaymentResponseDto;
import com.iksflow.assignment.web.dto.PaymentSaveRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class PaymentUtil {
    @Autowired
    JasyptConfig jasyptConfig;

    public String encrypt(String plainText) {
        return jasyptConfig.stringEncryptor().encrypt(plainText);
    }

    public String createUniqueId() {
        int random = Double.valueOf(Math.random() * 30).intValue();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String smallUuid = uuid.substring(random, random + 3);
        String timeId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String uniqueId = timeId + smallUuid;

        return uniqueId;
    }

    public String createPayStringFromDto(PaymentSaveRequestDto requestDto) {
        String formatString = "TESTFORMATSTRING";

        return formatString;
    }

    public PaymentResponseDto parsePayStringToDto(String payFormatted) {
        String tid = "TIDTESNUMBER";
        String aid = "AIDTESTNUMER";
        String cardNumber = "1234567890123456";
        String expiryMonthYear = "0620";
        String cvcNumber = "999";
        String installMonth = "00";
        BigDecimal totalAmount = BigDecimal.valueOf(100000);
        BigDecimal vatAmount = BigDecimal.ZERO;

        PaymentResponseDto responseDto = PaymentResponseDto.builder()
                .tid(tid)
                .aid(aid)
                .cardNumber(cardNumber)
                .expiryMonthYear(expiryMonthYear)
                .cvcNumber(cvcNumber)
                .installMonth(installMonth)
                .totalAmount(totalAmount)
                .vatAmount(vatAmount)
                .build();

        return responseDto;
    }
}
