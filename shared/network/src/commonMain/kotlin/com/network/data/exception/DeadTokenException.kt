package com.network.data.exception

class DeadTokenException(
    override val message: String?,
    override val cause: Throwable? = null
) : Exception(message, cause)