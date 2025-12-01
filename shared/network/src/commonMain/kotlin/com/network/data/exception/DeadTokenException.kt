package com.network.data.exception

class DeadTokenException(
    override val message: String?
) : Exception(message)