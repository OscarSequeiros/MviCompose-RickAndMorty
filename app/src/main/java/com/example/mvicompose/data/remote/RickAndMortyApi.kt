package com.example.mvicompose.data.remote

import com.example.mvicompose.data.remote.model.RemoteCharacters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character/")
    suspend fun getCharacters(@Query("page") value: Int): RemoteCharacters

}