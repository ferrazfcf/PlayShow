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
    token: AuthToken
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
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private fun HttpClientConfig<*>.installTimeout() {
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 10000
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

    suspend inline fun <reified T> HttpResponse.parseResponse(): Result<T> {
        return runCatching {
            when (status.value) {
                in 200..299 -> body<T>()
                in 400..499 -> {
                    val body = body<ErrorBody>()
                    throw ClientException(body.toString())
                }
                in 500..599 -> throw ServerException(status.description)
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
}
