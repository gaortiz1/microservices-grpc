package com.microservice.delivery.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "deliveries")
class Delivery(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    val address: String,
    val orderId: Int,
    val paymentId: Int,
    val status: DeliveryStatus,
)

enum class DeliveryStatus{
    RELEASED, CANCELED,
}