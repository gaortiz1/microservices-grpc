package com.microservice.order.application.controller

import com.microservice.application.service.order.grpc.OrderGRPC
import com.microservice.order.application.request.CreateOrderRequest
import org.apache.camel.ProducerTemplate
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController(
    private val producerTemplate: ProducerTemplate,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody createOrderRequest: CreateOrderRequest) {
        producerTemplate.requestBody(
            "direct:start-order-saga",
            OrderGRPC.CreateOrderRequest
                .newBuilder()
                .setName(createOrderRequest.name)
                .build(),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(OrderController::class.java)
    }
}