package com.microservice.delivery.repository

import com.microservice.delivery.model.Delivery
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRepository : CrudRepository<Delivery, Int>