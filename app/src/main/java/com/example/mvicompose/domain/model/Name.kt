package com.example.mvicompose.domain.model

import arrow.core.*

data class Name(val value: String) {

    companion object {

        fun of(value: String?): Option<Name> {
            return if (!value.isNullOrBlank()) {
                Name(value.capitalize()).some()
            } else {
                none()
            }
        }
    }
}