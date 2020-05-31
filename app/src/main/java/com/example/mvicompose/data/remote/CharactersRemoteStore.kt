package com.example.mvicompose.data.remote

import com.example.mvicompose.data.remote.model.RemoteCharacter
import io.reactivex.Single

class CharactersRemoteStore(private val api: RickAndMortyApi) {

    fun getAllCharacters(): Single<List<RemoteCharacter>> {
        return api.getCharacters()
            .map { it.results }
    }
}