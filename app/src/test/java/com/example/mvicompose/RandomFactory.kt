package com.example.mvicompose

import kotlin.random.Random

object RandomFactory {

    fun generateRandomString(length: Int = 20): String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun generateRandomInt(max: Int = Int.MAX_VALUE): Int {
        return Random.nextInt(0, max)
    }
}