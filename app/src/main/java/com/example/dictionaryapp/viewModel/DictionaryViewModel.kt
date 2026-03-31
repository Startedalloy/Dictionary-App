package com.example.dictionaryapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dictionaryapp.data.model.WordResponse
import com.example.dictionaryapp.data.repository.Resource
import com.example.dictionaryapp.data.repository.WordResponseRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DictionaryViewModel(private val wordResponseRepo: WordResponseRepo) : ViewModel() {

    private val _wordResponse = MutableStateFlow<Resource<List<WordResponse>>>(Resource.Loading())
    val wordResponse = _wordResponse.asStateFlow()

    fun getMeaning(word: String) {
        viewModelScope.launch {

            _wordResponse.value = Resource.Loading()

            var result = wordResponseRepo.getMeaning(word)

            _wordResponse.value = result

        }
    }
}