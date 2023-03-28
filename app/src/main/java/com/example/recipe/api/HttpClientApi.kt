package com.example.recipe.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

abstract class HttpClientApi {
    companion object {
        private var client : HttpClient? = null

        @Synchronized
        fun getClient() : HttpClient {
            if (client == null) {
                client = HttpClient(CIO) {
                    install(ContentNegotiation) {
                        json(Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        })
                    }
                }
            }

            return client!!
        }
    }
}