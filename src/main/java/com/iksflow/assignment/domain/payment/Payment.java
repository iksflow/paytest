package com.iksflow.assignment.domain.payment;

import com.iksflow.assignment.domain.BaseTimeEntity;
import com.iksflow.assignment.utils.PaymentUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Table(name = "payment")
@Entity
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "payment_tid", length = 20)
    private String tid;

    @Column(name = "payment_last_state", length = 10)
    private String lastState;

    @Builder
    public Payment(String tid, String lastState) {
        this.tid = createUniqueId();
        this.lastState = lastState;
    }

    private String createUniqueId() {
        int random = Double.valueOf(Math.random() * 30).intValue();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String smallUuid = uuid.substring(random, random + 3);
        String timeId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String uniqueId = timeId + smallUuid;

        return uniqueId;
    }
}
