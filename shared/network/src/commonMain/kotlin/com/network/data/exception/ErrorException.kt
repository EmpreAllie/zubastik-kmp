package com.network.data.exception

import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse

class ErrorException(
    request: HttpRequest,
    response: HttpResponse,
    val description: String?
) : ResponseException(request = request, response = response, responseMessage = description.orEmpty()) {
    override val message: String?
        get() = description ?: super.message
}
