package com.example.mvicompose.domain.repository

import com.example.mvicompose.domain.model.Character
import io.reactivex.Single

interface CharacterRepository {

    suspend fun getAll(): List<Character>
}