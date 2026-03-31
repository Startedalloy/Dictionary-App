package com.example.dictionaryapp.data

import com.example.dictionaryapp.data.model.WordResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{word}")
    suspend fun getMeaning(
        @Path("word") searchQuery: String
    ): List<WordResponse>


}