package com.example.mvicompose.data.remote

import com.example.mvicompose.data.remote.model.RemoteCharacters
import io.reactivex.Single
import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("character/?page=12")
    fun getCharacters(): Single<RemoteCharacters>

}