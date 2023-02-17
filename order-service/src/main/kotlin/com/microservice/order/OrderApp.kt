package com.microservice.order

import io.grpc.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class OrderApp(val server: Server) {
    fun runGrpcServer(){
        server.start()
        server.awaitTermination();
    }
}

fun main(args: Array<String>) {
    val context = runApplication<OrderApp>(*args)
    val app = context.getBean(OrderApp::class.java)
    app.runGrpcServer()
}
