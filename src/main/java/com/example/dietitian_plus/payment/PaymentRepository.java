package com.example.dietitian_plus.payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByExternalPaymentId(String externalPaymentId);

}
