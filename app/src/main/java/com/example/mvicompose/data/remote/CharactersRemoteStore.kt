package com.example.mvicompose.data.remote

import com.example.mvicompose.data.remote.model.RemoteCharacter
import io.reactivex.Single
import kotlin.random.Random

class CharactersRemoteStore(private val api: RickAndMortyApi) {

    // It allows us to get different characters for every request:
    private val random = Random(System.currentTimeMillis())

    suspend fun getAllCharacters(): List<RemoteCharacter> {
        return api.getCharacters(random.nextInt(1, 16)).results
    }
}