package com.microservice.order.config

import com.microservice.order.grpc.OrderServiceGrpcImpl
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfiguration{

    @Bean
    fun server(orderServiceGrpc: OrderServiceGrpcImpl): Server =
        ServerBuilder
            .forPort(9899)
            .addService(orderServiceGrpc)
            .build()
}