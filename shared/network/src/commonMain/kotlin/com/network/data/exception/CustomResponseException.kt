package com.network.data.exception

import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse

class CustomResponseException(
    request: HttpRequest,
    response: HttpResponse,
    responseMessage: String,
    val responseBody: String?,
    val responseStatus: Int = 0,
) : ResponseException(request, response, responseMessage) {
    @Suppress("MagicNumber")
    fun isServerError(): Boolean {
        return (responseStatus in 500..599)
    }
}

fun Exception.isServerError(): Boolean {
    return if (this is CustomResponseException) {
        this.isServerError()
    } else {
        false
    }
}
