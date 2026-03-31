package com.example.dictionaryapp.data.model

data class WordResponse(
    val word: String,
    val phonetic: List<Phonetic>,
    val meanings: List<Meaning>
)

data class Phonetic(
    val text: String?,
    val audio: String?
)

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)

data class Definition(
    val definition: String,
    val example: String?
)

