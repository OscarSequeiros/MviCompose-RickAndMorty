package com.example.mvicompose.domain.model

sealed class Status {

    object Alive : Status()

    object Dead : Status()

    object Unknown : Status()

    companion object {

        fun of(value: String?): Status {
            return when (value) {
                "Alive" -> Alive
                "Dead" -> Dead
                else -> Unknown
            }
        }
    }
}