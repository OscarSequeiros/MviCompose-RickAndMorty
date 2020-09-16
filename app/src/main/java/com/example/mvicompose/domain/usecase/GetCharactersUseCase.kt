package com.example.mvicompose.domain.usecase

import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.repository.CharacterRepository
import io.reactivex.Single

class GetCharactersUseCase(private val repository: CharacterRepository) {

    suspend operator fun invoke(): List<Character> {
        return repository.getAll()
    }
}