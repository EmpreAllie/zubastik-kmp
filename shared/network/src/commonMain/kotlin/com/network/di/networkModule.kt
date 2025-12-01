package com.network.di

import com.core.data.infrastructure.KeyValueStorage
import com.core.data.utils.NativeHost
import com.network.api.apis.PetApi
import com.network.api.apis.StoreApi
import com.network.api.apis.UserApi
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
    single<HttpClientConfig<*>.() -> Unit> {
        createHttpClientConfig(
            authApi = get(),
            json = get(),
        )
    }

    single<UserApi> {
        val httpClient = HttpClient()

        UserApi(
            baseUrl = baseUrl,
            httpClientConfig = createHttpClientConfig(
                authApi = null,
                json = get(),
            ),
            httpClientEngine = httpClient.engine
        )
    }
    single<PetApi> {
        val httpClient: HttpClient = get()

        PetApi(
            baseUrl = baseUrl,
            httpClientConfig = get(),
            httpClientEngine = httpClient.engine
        )
    }
    single<StoreApi> {
        val httpClient: HttpClient = get()

        StoreApi(
            baseUrl = baseUrl,
            httpClientConfig = get(),
            httpClientEngine = httpClient.engine
        )
    }
}

private fun createHttpClientConfig(
    authApi: UserApi? = null,
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

    if (authApi != null && keyValueStorage.isHaveTokens()) {
        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = keyValueStorage.accessToken
                    val refreshToken = keyValueStorage.refreshToken

                    BearerTokens(accessToken.orEmpty(), refreshToken.orEmpty())
                }
                refreshTokens {
                    try {
                        /* val refreshToken = keyValueStorage.refreshToken
                         val result =
                             authApi.doRefresh(refresh = Refresh(refreshToken))
                                 .decode<TokenResponse>().result
                         keyValueStorage.accessToken = result?.accessToken
                         keyValueStorage.refreshToken = result?.refreshToken
                         result?.let {
                             BearerTokens(it.accessToken!!, it.refreshToken!!)
                         }*/
                        BearerTokens("", "")
                    } catch (e: CustomResponseException) {
                        if (e.isUnauthorized) {
                            keyValueStorage.clearTokens()
                            throw DeadTokenException(message = e.message)
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