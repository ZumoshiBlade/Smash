package com.zushi.smash


import com.zushi.smash.models.CharactersResponse
import com.zushi.smash.models.CharactersResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET()
    suspend fun getCharacters(@Url url:String): Response<List<CharactersResponseItem>>
}