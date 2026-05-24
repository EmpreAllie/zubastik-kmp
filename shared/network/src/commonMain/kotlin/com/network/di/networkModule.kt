package com.network.di

import com.core.data.infrastructure.KeyValueStorage
import com.core.data.utils.NativeHost
import com.features.base.AppEvent
import com.features.base.AuthEventBus
import com.network.api.apis.AuthApi
import com.network.api.apis.CalendarApi
import com.network.api.apis.AiChatApi
import com.network.api.apis.LecturesApi
import com.network.api.apis.ProfileApi
import com.network.api.models.TokenPair
import com.network.data.exception.CustomExceptionParser
import com.network.data.exception.CustomResponseException
import com.network.data.exception.DeadTokenException
import com.network.data.exception.ExceptionPlugin
import com.network.data.exception.HttpExceptionFactory
import com.network.data.exception.ValidationExceptionParser
import com.network.data.plugin.createHttpClientEngine
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

val networkModule: Module = module {
    val baseUrl: String by lazy { getKoin().get<NativeHost>().getUrl() }

    single<Json> { Json { ignoreUnknownKeys = true } }

    single<AuthApi> {
        val httpClient = HttpClient()
        AuthApi(
            baseUrl = baseUrl,
            httpClientConfig = createHttpClientConfig(
                authApi = null,
                json = get()
            ),
            httpClientEngine = httpClient.engine
        )
    }

    single<ProfileApi> {
        val httpClient = HttpClient()
        ProfileApi(
            baseUrl = baseUrl,
            httpClientConfig = createHttpClientConfig(
                authApi = get(),
                json = get()
            ),
            httpClientEngine = httpClient.engine
        )
    }

    single<CalendarApi> {
        val httpClient = HttpClient()
        CalendarApi(
            baseUrl = baseUrl,
            httpClientConfig = createHttpClientConfig(
                authApi = get(),
                json = get()
            ),
            httpClientEngine = httpClient.engine
        )
    }

    single<AiChatApi> {
        val httpClient = HttpClient()
        AiChatApi(
            baseUrl = baseUrl,
            httpClientConfig = createHttpClientConfig(
                authApi = get(),
                json = get()
            ),
            httpClientEngine = httpClient.engine
        )
    }

    single<LecturesApi> {
        val httpClient = HttpClient()
        LecturesApi(
            baseUrl = baseUrl,
            httpClientConfig = createHttpClientConfig(
                authApi = get(),
                json = get()
            ),
            httpClientEngine = httpClient.engine
        )
    }
}

private fun createHttpClientConfig(
    authApi: AuthApi? = null,
    json: Json,
): HttpClientConfig<*>.() -> Unit = {
    val keyValueStorage: KeyValueStorage = getKoin().get()

    createHttpClientEngine {
        expectSuccess = true
    }

    install(ContentNegotiation) {
        json(
            json = Json { ignoreUnknownKeys = true },
            contentType = ContentType.Any
        )
    }

    install(ExceptionPlugin) {
        exceptionFactory = HttpExceptionFactory(
            defaultParser = CustomExceptionParser(json),
            customParsers = mapOf(
                HttpStatusCode.UnprocessableEntity.value to ValidationExceptionParser(json)
            )
        )
    }

    install(Logging) {
        level = LogLevel.ALL
        logger = Logger.DEFAULT
    }

    if (authApi != null && keyValueStorage.hasTokens()) {
        install(Auth) {
            bearer {

                loadTokens {
                    val accessToken = keyValueStorage.accessToken
                    val refreshToken = keyValueStorage.refreshToken

                    BearerTokens(accessToken.orEmpty(), refreshToken.orEmpty())
                }

                refreshTokens {
                    try {
                        val accessToken = keyValueStorage.accessToken.orEmpty()
                        val refreshToken = keyValueStorage.refreshToken.orEmpty()

                        val result =
                            authApi.apiV1AuthRefreshTokenPost(
                                TokenPair(
                                    accessToken = accessToken,
                                    refreshToken = refreshToken
                                )
                            ).body()

                        keyValueStorage.accessToken = result.accessToken
                        keyValueStorage.refreshToken = result.refreshToken

                        BearerTokens(result.accessToken.orEmpty(), result.refreshToken.orEmpty())
                    } catch (e: CustomResponseException) {
                        if (e.isUnauthorized) {
                            keyValueStorage.clearTokens()

                            AuthEventBus.send(AppEvent.Logout)

                            throw DeadTokenException(
                                message = e.message,
                                cause = e
                            )
                        }
                        throw e
                    } catch (e: Exception) {
                        throw e
                    }
                }
            }
        }
    }
}