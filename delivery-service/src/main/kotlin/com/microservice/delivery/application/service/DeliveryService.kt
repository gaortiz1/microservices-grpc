package com.microservice.delivery.application.service

import com.microservice.delivery.application.request.CreateDeliveryRequest
import com.microservice.delivery.model.Delivery
import com.microservice.delivery.model.DeliveryStatus
import com.microservice.delivery.repository.DeliveryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeliveryService(
    private val deliveryRepository: DeliveryRepository
) {

    @Transactional
    fun createDelivery(createDeliveryRequest: CreateDeliveryRequest): Delivery {
        throw IllegalArgumentException()
        return deliveryRepository.save(
            Delivery(
                address = createDeliveryRequest.address,
                orderId = createDeliveryRequest.orderId,
                paymentId = createDeliveryRequest.paymentId,
                status = DeliveryStatus.RELEASED
            )
        )
    }

}