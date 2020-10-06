package com.example.mvicompose.data.remote

import com.example.mvicompose.data.remote.model.RemoteCharacter
import com.example.mvicompose.data.remote.model.RemoteCharacters
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class CharactersRemoteStore @Inject constructor() {

    @KtorExperimentalAPI
    private val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    suspend fun getAllCharacters(): List<RemoteCharacter> {
        // It allows us to get different characters for every request:CharactersViewModel
        val page = (1..20).random()

        val requestUrl = "https://rickandmortyapi.com/api/character/?page=$page"
        val response = client.get<RemoteCharacters>(requestUrl)
        return response.results
    }
}