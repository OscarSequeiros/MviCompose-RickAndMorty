package com.example.mvicompose.domain.model

sealed class Gender {

    object Female : Gender()

    object Male : Gender()

    object Genderless: Gender()

    object Unknown : Gender()

    companion object {

        fun of(value: String?): Gender {
            return when (value) {
                "Male" -> Male
                "Female" -> Female
                "Genderless" -> Genderless
                else -> Unknown
            }
        }
    }
}