package com.example.mvicompose.data

import com.example.mvicompose.data.remote.CharactersRemoteStore
import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.repository.CharacterRepository
import io.reactivex.Single
import javax.inject.Inject

class CharacterDataRepository @Inject constructor(
    private val remoteStore: CharactersRemoteStore,
    private val dataMapper: DataCharacterMapper
) : CharacterRepository {

    override fun getAll(): Single<List<Character>> {
        return remoteStore
            .getAllCharacters()
            .map { dataMapper.map(it) }
    }
}