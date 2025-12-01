package com.network.data.exception

import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse

interface ExceptionFactory {
    fun createException(
        request: HttpRequest,
        response: HttpResponse,
        responseBody: String?
    ): ResponseException
}
