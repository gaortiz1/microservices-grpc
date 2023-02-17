package com.microservice.delivery.application.controller

import com.microservice.delivery.application.request.CreateDeliveryRequest
import com.microservice.delivery.application.service.DeliveryService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/delivery")
class DeliveryController(
    private val deliveryService: DeliveryService,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody createDeliveryRequest: CreateDeliveryRequest) {
        deliveryService.createDelivery(createDeliveryRequest)
    }

}