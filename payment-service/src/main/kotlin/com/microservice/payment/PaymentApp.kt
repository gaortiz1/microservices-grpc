package com.microservice.payment

import io.grpc.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class PaymentApp(val server: Server) {
    fun runGrpcServer(){
        server.start()
        server.awaitTermination();
    }

}

fun main(args: Array<String>) {
    val context = runApplication<PaymentApp>(*args)
    val app = context.getBean(PaymentApp::class.java)
    app.runGrpcServer()
}