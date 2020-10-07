package com.example.mvicompose.data.remote

import com.example.mvicompose.data.remote.model.RemoteCharacter
import com.example.mvicompose.data.remote.model.RemoteCharacters
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class CharactersRemoteStore @Inject constructor() {

    private val client = HttpClient(CIO) {
        engine {
            endpoint {
                connectTimeout = 20_000
            }
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    suspend fun getAllCharacters(): List<RemoteCharacter> {
        // It allows us to get different characters for every request:
        val page = (1..20).random()

        val requestUrl = "https://rickandmortyapi.com/api/character"
        val response = client.get<RemoteCharacters>(requestUrl) {
            parameter("page", page)
        }
        return response.results
    }
}