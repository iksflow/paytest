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

    public String decrypt(String cipherText) { return jasyptConfig.stringEncryptor().decrypt(cipherText); }

    public String createUniqueId() {
        int random = Double.valueOf(Math.random() * 30).intValue();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String smallUuid = uuid.substring(random, random + 3);
        String timeId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String uniqueId = timeId + smallUuid;

        return uniqueId;
    }
}
