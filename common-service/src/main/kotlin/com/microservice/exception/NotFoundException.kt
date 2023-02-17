package com.microservice.exception

class NotFoundException(
    message: String,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)