package com.example.mvicompose.domain.usecase

import com.example.mvicompose.domain.model.Character
import com.example.mvicompose.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository) {

    suspend operator fun invoke(): List<Character> {
        return repository.getAll()
    }
}