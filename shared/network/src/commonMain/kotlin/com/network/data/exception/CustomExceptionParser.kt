package com.network.data.exception


import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


class CustomExceptionParser(private val json: Json) : HttpExceptionFactory.HttpExceptionParser {

    @Suppress("NestedBlockDepth")
    override fun parseException(
        request: HttpRequest,
        response: HttpResponse,
        responseBody: String?
    ): ResponseException? {
        @Suppress("TooGenericExceptionCaught", "SwallowedException")
        return try {
            val body = responseBody.orEmpty()
            val jsonObject = json.parseToJsonElement(body).jsonObject
            val responseMessage = jsonObject.get(ERROR_MESSAGE)?.jsonPrimitive?.content.orEmpty()
            val responseStatus: Int = response.status.value

            CustomResponseException(
                request = request,
                response = response,
                responseMessage = responseMessage,
                responseStatus = responseStatus,
                responseBody = responseBody
            )
        } catch (exception: Exception) {
            null
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "errorMessage"
    }
}
