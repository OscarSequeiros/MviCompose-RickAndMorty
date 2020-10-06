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

    override suspend fun getAll(): List<Character> {
        val remoteCharacters = remoteStore.getAllCharacters()
        return dataMapper.map(remoteCharacters)
    }
}