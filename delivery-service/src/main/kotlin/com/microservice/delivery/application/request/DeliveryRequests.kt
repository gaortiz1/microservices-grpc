package com.microservice.delivery.application.request

data class CreateDeliveryRequest(
    val address: String,
    val orderId: Int,
    val paymentId: Int,
)