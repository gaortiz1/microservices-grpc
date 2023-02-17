package com.microservice.delivery.grpc

import com.microservice.application.service.delivery.grpc.DeliveryGRPC.DeliveryResponse
import com.microservice.application.service.delivery.grpc.DeliveryGRPC.DeliveryStatus
import com.microservice.application.service.delivery.grpc.DeliveryServiceGrpc
import com.microservice.delivery.application.request.CreateDeliveryRequest
import com.microservice.delivery.application.service.DeliveryService
import com.microservice.delivery.model.Delivery
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Service
import com.microservice.application.service.delivery.grpc.DeliveryGRPC.CreateDeliveryRequest as CreateDeliveryRequestGrpc

@Service
class DeliveryServiceGrpcImpl(
    private val deliveryService: DeliveryService,
): DeliveryServiceGrpc.DeliveryServiceImplBase() {

    override fun createDelivery(
        request: CreateDeliveryRequestGrpc,
        responseObserver: StreamObserver<DeliveryResponse>
    ) {
        val delivery = deliveryService.createDelivery(
            CreateDeliveryRequest(
                address = request.address,
                orderId = request.orderId,
                paymentId = request.paymentId
            )
        )

        responseObserver.onNext(delivery.toGrpc())
        responseObserver.onCompleted()
    }

    private fun Delivery.toGrpc(): DeliveryResponse =
        DeliveryResponse
            .newBuilder()
            .setId(this.id!!)
            .setOrderId(this.orderId)
            .setPaymentId(this.paymentId)
            .setAddress(address)
            .setStatus(DeliveryStatus.valueOf(this.status.name))
            .build()
}