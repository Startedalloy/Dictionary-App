package com.example.dictionaryapp.data.repository

import com.example.dictionaryapp.data.ApiService
import com.example.dictionaryapp.data.model.WordResponse

class WordResponseRepo(private val api: ApiService) {
    suspend fun getMeaning(searchQuery: String): Resource<List<WordResponse>> {
        return try {
            val response = api.getMeaning(searchQuery)

            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred")
        }

    }
}


sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}