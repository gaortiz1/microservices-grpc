package com.microservice.order.grpc

import com.microservice.application.service.order.grpc.OrderGRPC
import com.microservice.application.service.order.grpc.OrderGRPC.OrderResponse
import com.microservice.application.service.order.grpc.OrderGRPC.OrderStatus
import com.microservice.application.service.order.grpc.OrderServiceGrpc.OrderServiceImplBase
import com.microservice.order.application.request.CancelOrderRequest
import com.microservice.order.application.request.CreateOrderRequest
import com.microservice.order.application.service.OrderService
import com.microservice.order.model.Order
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Service

@Service
class OrderServiceGrpcImpl(
    private val orderService: OrderService,
): OrderServiceImplBase() {
    override fun createOrder(
        request: OrderGRPC.CreateOrderRequest,
        responseObserver: StreamObserver<OrderResponse>
    ) {
        val order = orderService.createOrder(
            CreateOrderRequest(
                request.name,
            )
        )

        responseObserver.onNext(order.toGrpc())
        responseObserver.onCompleted()
    }

    override fun cancelOrder(
        request: OrderGRPC.CancelOrderRequest,
        responseObserver: StreamObserver<OrderResponse>
    ) {

        val order = orderService.cancelOrder(
            CancelOrderRequest(
                request.orderId
            )
        )

        responseObserver.onNext(order.toGrpc())
        responseObserver.onCompleted()
    }

    private fun Order.toGrpc(): OrderResponse =
        OrderResponse
            .newBuilder()
            .setId(this.id!!)
            .setName(this.name)
            .setStatus(OrderStatus.valueOf(this.status.name))
            .build()
}