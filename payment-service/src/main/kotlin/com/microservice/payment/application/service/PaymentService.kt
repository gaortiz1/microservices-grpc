package com.microservice.payment.application.service

import com.microservice.exception.NotFoundException
import com.microservice.payment.application.request.ChargePaymentRequest
import com.microservice.payment.application.request.RefundPaymentRequest
import com.microservice.payment.model.Payment
import com.microservice.payment.model.PaymentStatus
import com.microservice.payment.repository.PaymentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {

    @Transactional
    fun chargePayment(chargePaymentRequest: ChargePaymentRequest): Payment {

        //throw IllegalArgumentException("test saga")

        return paymentRepository.save(
            Payment(
                name = chargePaymentRequest.name,
                orderId = chargePaymentRequest.orderId,
                status = PaymentStatus.PROCESS
            )
        )
    }

    @Transactional
    fun refundPayment(refundPaymentRequest: RefundPaymentRequest): Payment {

        val payment = paymentRepository.findById(refundPaymentRequest.paymentId)
            .orElseThrow { throw NotFoundException("payment_not_found") }

        payment.refunded()

        return paymentRepository.save(payment)
    }

}