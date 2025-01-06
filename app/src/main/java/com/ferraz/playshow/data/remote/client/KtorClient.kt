package com.ferraz.playshow.data.remote.client

import com.ferraz.playshow.core.exceptions.ClientException
import com.ferraz.playshow.core.exceptions.ServerException
import com.ferraz.playshow.core.exceptions.UnexpectedException
import com.ferraz.playshow.data.remote.model.AuthToken
import com.ferraz.playshow.data.remote.model.BaseUrl
import com.ferraz.playshow.data.remote.model.ErrorBody
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
class KtorClient(
    engine: HttpClientEngine,
    baseUrl: BaseUrl,
    token: AuthToken,
    private val requestTimeout: Long = DEFAULT_TIMEOUT,
    private val connectTimeout: Long = DEFAULT_TIMEOUT,
    private val socketTimeout: Long = DEFAULT_TIMEOUT
) {

    val client: HttpClient by lazy {
        HttpClient(engine) {
            installContentNegotiationJson()
            installTimeout()
            installDefaults(baseUrl, token)
        }
    }

    private fun HttpClientConfig<*>.installContentNegotiationJson() {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private fun HttpClientConfig<*>.installTimeout() {
        install(HttpTimeout) {
            requestTimeoutMillis = requestTimeout
            connectTimeoutMillis = connectTimeout
            socketTimeoutMillis = socketTimeout
        }
    }

    private fun HttpClientConfig<*>.installDefaults(baseUrl: BaseUrl, authToken: AuthToken) {
        defaultRequest {
            url(baseUrl.url)
            authToken.token?.let { token -> header(HttpHeaders.Authorization, "Bearer $token") }
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    @Suppress("ThrowsCount")
    suspend inline fun <reified T> HttpResponse.parseResponse(): Result<T> {
        return runCatching {
            when (status.value) {
                in SUCCESS_CODES -> body<T>()
                in CLIENT_ERROR_CODES -> {
                    val body = body<ErrorBody>()
                    throw ClientException(body.toString())
                }
                in SERVER_ERROR_CODES -> throw ServerException(status.description)
                else -> throw UnexpectedException(status.description)
            }
        }
    }

    suspend inline fun <reified T> get(path: String): Result<T> {
        return client.get(path).parseResponse()
    }

    suspend inline fun <reified T, reified B> post(path: String, body: B): Result<T> {
        return client.post(path) {
            setBody(body)
        }.parseResponse()
    }

    companion object {
        private const val DEFAULT_TIMEOUT = 10000L
        val SUCCESS_CODES = 200..299
        val CLIENT_ERROR_CODES = 400..499
        val SERVER_ERROR_CODES = 500..599
    }
}
