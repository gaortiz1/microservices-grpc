package com.microservice.payment.application.request

data class ChargePaymentRequest(
    val name: String,
    val orderId: Int
)

data class RefundPaymentRequest(
    val paymentId: Int,
)