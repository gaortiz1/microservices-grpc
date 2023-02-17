package com.microservice.order.saga

import com.microservice.application.service.delivery.grpc.DeliveryGRPC
import com.microservice.application.service.order.grpc.OrderGRPC
import com.microservice.application.service.payment.grpc.PaymentGRPC
import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

@Component
class OrderSagaService : RouteBuilder() {
    override fun configure() {

        from("direct:start-order-saga")
            .to("direct:createOrder")
            .to("direct:chargePayment")
            .to("direct:createDelivery")

        orderServiceRoute()
        paymentServiceRoute()
        deliveryServiceRoute()
    }

    private fun orderServiceRoute() {

        from("direct:createOrder")
            .doTry()
                .to("grpc://$ORDER_SERVICE_HOST/com.microservice.application.service.order.grpc.OrderService?method=createOrder&synchronous=true")
                .setHeader("orderId", simple("\${body.id}"))
                .setHeader("name", simple("\${body.name}"))
            .doCatch(Exception::class.java)
                .to("log:Failed to cancel saga")
            .endDoTry();

        from("direct:cancelOrder")
            .doTry()
                .process {
                    val cancelOrderRequest = OrderGRPC.CancelOrderRequest.newBuilder()
                        .setOrderId(it.message.getHeader("orderId", Integer::class.java).toInt())
                        .build()
                    it.`in`.body = cancelOrderRequest
                }
                .to("grpc://$ORDER_SERVICE_HOST/com.microservice.application.service.order.grpc.OrderService?method=cancelOrder&synchronous=true")
            .doCatch(Exception::class.java)
                .to("log:Failed to cancel saga")
            .endDoTry();
    }

    private fun paymentServiceRoute() {
        from("direct:chargePayment")
            .doTry()
                .process {
                    val chargePaymentRequest = PaymentGRPC.ChargePaymentRequest.newBuilder()
                        .setName(it.message.getHeader("name", String()::class.java))
                        .setOrderId(it.message.getHeader("orderId", Integer::class.java).toInt())
                        .build()
                    it.`in`.body = chargePaymentRequest
                }
                .to("grpc://$PAYMENT_SERVICE_HOST/com.microservice.application.service.payment.grpc.PaymentService?method=chargePayment&synchronous=true")
                .setHeader("paymentId", simple("\${body.id}"))
            .doCatch(Exception::class.java)
                .to("log:Failed to cancel saga")
            .endDoTry();

        from("direct:refundPayment")
            .doTry()
                .process {
                    val chargePaymentRequest = PaymentGRPC.RefundPaymentRequest.newBuilder()
                        .setPaymentId(it.message.getHeader("orderId", Integer::class.java).toInt())
                        .build()
                    it.`in`.body = chargePaymentRequest
                }
                .to("grpc://$PAYMENT_SERVICE_HOST/com.microservice.application.service.payment.grpc.PaymentService?method=refundPayment&synchronous=true")
                .to("direct:cancelOrder")
            .doCatch(Exception::class.java)
                .to("log:Failed to cancel saga")
            .endDoTry();
    }

    private fun deliveryServiceRoute() {
        from("direct:createDelivery")
            .doTry()
                .process {
                    val chargePaymentRequest = DeliveryGRPC.CreateDeliveryRequest.newBuilder()
                        .setAddress(it.message.getHeader("name", String()::class.java))
                        .setOrderId(it.message.getHeader("orderId", Integer::class.java).toInt())
                        .setPaymentId(it.message.getHeader("orderId", Integer::class.java).toInt())
                        .build()
                    it.`in`.body = chargePaymentRequest
                }
                .to("grpc://$DELIVERY_SERVICE_HOST/com.microservice.application.service.delivery.grpc.DeliveryService?method=createDelivery&synchronous=true")
                .setHeader("deliveryId", simple("\${body.id}"))
            .doCatch(Exception::class.java)
                .to("direct:refundPayment")
            .endDoTry();
    }

    companion object {
        const val ORDER_SERVICE_HOST = "localhost:9899"
        const val PAYMENT_SERVICE_HOST = "localhost:9892"
        const val DELIVERY_SERVICE_HOST = "localhost:9895"
    }
}