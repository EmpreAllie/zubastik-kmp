package com.network.data.exception

import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse

class HttpExceptionFactory(
    private val defaultParser: HttpExceptionParser,
    private val customParsers: Map<Int, HttpExceptionParser>
) : ExceptionFactory {

    override fun createException(
        request: HttpRequest,
        response: HttpResponse,
        responseBody: String?
    ): ResponseException {
        val parser = customParsers[response.status.value] ?: defaultParser

        val exception = parser.parseException(request, response, responseBody)

        return exception ?: ResponseException(
            request = request,
            response = response,
            responseMessage = responseBody.orEmpty()
        )
    }

    fun interface HttpExceptionParser {
        fun parseException(
            request: HttpRequest,
            response: HttpResponse,
            responseBody: String?
        ): ResponseException?
    }
}
