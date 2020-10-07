package com.example.mvicompose.domain.repository

import com.example.mvicompose.domain.model.Character

interface CharacterRepository {

    suspend fun getAll(): List<Character>
}