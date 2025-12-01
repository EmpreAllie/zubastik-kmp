package com.network.data.exception

import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class ValidationExceptionParser(private val json: Json) : HttpExceptionFactory.HttpExceptionParser {

    @Suppress("ReturnCount", "NestedBlockDepth")
    override fun parseException(
        request: HttpRequest,
        response: HttpResponse,
        responseBody: String?
    ): ResponseException? {
        @Suppress("TooGenericExceptionCaught", "SwallowedException")
        try {
            val body = responseBody.orEmpty()
            val jsonRoot = json.parseToJsonElement(body)
            if (jsonRoot is JsonObject) {
                val error = jsonRoot.jsonObject.getValue(JSON_ERROR_KEY).jsonObject

                return ErrorException(
                    request = request,
                    response = response,
                    description = error.getValue(JSON_MESSAGE_KEY).jsonPrimitive.content
                )
            } else if (jsonRoot is JsonArray) {
                val errorsJson = jsonRoot.jsonArray

                val errors = ArrayList<ValidationException.Error>(errorsJson.size)

                errorsJson.forEach { item ->
                    try {
                        val jsonObject = item.jsonObject

                        val message: String
                        val field: String

                        if (jsonObject.containsKey(JSON_MESSAGE_KEY)) {
                            message = jsonObject.getValue(JSON_MESSAGE_KEY).jsonPrimitive.content
                        } else {
                            return@forEach
                        }

                        if (jsonObject.containsKey(JSON_FIELD_KEY)) {
                            field = jsonObject.getValue(JSON_FIELD_KEY).jsonPrimitive.content
                        } else {
                            return@forEach
                        }

                        errors.add(ValidationException.Error(field, message))
                    } catch (e: Exception) {
                        // ignore item
                    }
                }

                return ValidationException(
                    request = request,
                    response,
                    responseBody.orEmpty(),
                    errors
                )
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }
    }

    companion object {
        private const val JSON_MESSAGE_KEY = "message"
        private const val JSON_FIELD_KEY = "field"
        private const val JSON_ERROR_KEY = "error"
        private const val JSON_CODE_KEY = "code"
    }
}
