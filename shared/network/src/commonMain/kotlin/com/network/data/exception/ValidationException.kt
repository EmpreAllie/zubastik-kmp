package com.network.data.exception

import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse

class ValidationException(
    request: HttpRequest,
    response: HttpResponse,
    message: String,
    val errors: List<Error>
) : ResponseException(request, response, message) {
    data class Error(val field: String, val message: String)
}
