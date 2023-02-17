package com.microservice.order.application.request

data class CreateOrderRequest(
    val name: String,
)

data class CancelOrderRequest(
    val orderId: Int,
)