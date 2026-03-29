package com.example.dictionaryapp

class WordResponseRepo {
    suspend fun getMeaning(searchQuery: String): List<WordResponse> {
        return RetrofitBuilder.apiService.getMeaning(searchQuery)
    }
    }