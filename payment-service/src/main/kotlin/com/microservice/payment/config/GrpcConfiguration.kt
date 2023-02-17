package com.microservice.payment.config

import com.microservice.payment.grpc.PaymentServiceGrpcImpl
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfiguration {

    @Bean
    fun server(paymentServiceGrpcImpl: PaymentServiceGrpcImpl): Server =
        ServerBuilder
            .forPort(9892)
            .addService(paymentServiceGrpcImpl)
            .build()
}