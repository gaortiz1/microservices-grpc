package com.microservice.payment.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "payments")
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    val orderId: Int,
    val name: String,
    @Enumerated(EnumType.STRING)
    var status: PaymentStatus,
) {

    fun refunded() {
        status = PaymentStatus.REFUNDED
    }

}

enum class PaymentStatus{
    REFUNDED, PROCESS,
}