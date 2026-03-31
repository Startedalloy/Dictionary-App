package com.example.dictionaryapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASEURL = "https://api.dictionaryapi.dev/api/v2/entries/en/"

    val apiService: ApiService by lazy {
        Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

    }


}