package com.microservice.delivery

import io.grpc.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class DeliveryApp(val server: Server) {
    fun runGrpcServer(){
        server.start()
        server.awaitTermination();
    }
}

fun main(args: Array<String>) {
    val context = runApplication<DeliveryApp>(*args)
    val app = context.getBean(DeliveryApp::class.java)
    app.runGrpcServer()
}
