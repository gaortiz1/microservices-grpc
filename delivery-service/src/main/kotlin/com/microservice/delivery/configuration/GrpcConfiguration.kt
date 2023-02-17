package com.microservice.delivery.configuration

import com.microservice.application.service.delivery.grpc.DeliveryServiceGrpc
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfiguration {

    @Bean
    fun server(deliveryServiceGrpcImpl: DeliveryServiceGrpc.DeliveryServiceImplBase): Server =
        ServerBuilder
            .forPort(9895)
            .addService(deliveryServiceGrpcImpl)
            .build()
}