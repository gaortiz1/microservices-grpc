package com.microservice.order.config

import com.microservice.order.saga.OrderSagaService
import org.apache.camel.CamelContext
import org.apache.camel.ProducerTemplate
import org.apache.camel.impl.DefaultCamelContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CamelConfiguration {

    @Bean
    fun camelContext(): CamelContext {
        val context = DefaultCamelContext()
        context.addRoutes(OrderSagaService())
        return context
    }

    @Bean
    fun producerTemplate(camelContext: CamelContext): ProducerTemplate {
        camelContext.start()
        return camelContext.createProducerTemplate()
    }
}