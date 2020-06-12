package com.iksflow.assignment.domain.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, String> {
    @Query("SELECT p FROM PaymentDetail p WHERE p.aid = ?1")
    PaymentDetail findOneByAid(String aid);
}
