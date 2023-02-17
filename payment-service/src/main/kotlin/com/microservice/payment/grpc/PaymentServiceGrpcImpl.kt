package com.microservice.payment.grpc

import com.microservice.application.service.payment.grpc.PaymentGRPC.PaymentResponse
import com.microservice.application.service.payment.grpc.PaymentGRPC.PaymentStatus
import com.microservice.application.service.payment.grpc.PaymentServiceGrpc.PaymentServiceImplBase
import com.microservice.payment.application.request.ChargePaymentRequest
import com.microservice.payment.application.request.RefundPaymentRequest
import com.microservice.payment.application.service.PaymentService
import com.microservice.payment.model.Payment
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Service
import com.microservice.application.service.payment.grpc.PaymentGRPC.ChargePaymentRequest as ChargePaymentRequestGrpc
import com.microservice.application.service.payment.grpc.PaymentGRPC.RefundPaymentRequest as RefundPaymentRequestGrpc

@Service
class PaymentServiceGrpcImpl(
    private val paymentService: PaymentService,
): PaymentServiceImplBase() {
    override fun chargePayment(
        request: ChargePaymentRequestGrpc,
        responseObserver: StreamObserver<PaymentResponse>
    ) {
        val payment = paymentService.chargePayment(
            ChargePaymentRequest(
                request.name,
                request.orderId,
            )
        )

        responseObserver.onNext(payment.toGrpc())
        responseObserver.onCompleted()
    }

    override fun refundPayment(
        request: RefundPaymentRequestGrpc,
        responseObserver: StreamObserver<PaymentResponse>
    ) {
        val refundPayment = paymentService.refundPayment(
            RefundPaymentRequest(
                request.paymentId
            )
        )
        responseObserver.onNext(refundPayment.toGrpc())
        responseObserver.onCompleted()
    }

    fun Payment.toGrpc(): PaymentResponse =
        PaymentResponse
            .newBuilder()
            .setId(this.id!!)
            .setName(this.name)
            .setOrderId(this.orderId)
            .setStatus(PaymentStatus.valueOf(this.status.name))
            .build()
}