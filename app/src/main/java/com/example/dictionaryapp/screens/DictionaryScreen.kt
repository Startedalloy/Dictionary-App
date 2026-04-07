package com.example.dictionaryapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.dictionaryapp.data.model.WordResponse
import com.example.dictionaryapp.data.repository.Resource
import com.example.dictionaryapp.viewModel.DictionaryViewModel

@Composable
fun DictionaryScreen(
    viewModel: DictionaryViewModel = hiltViewModel()
) {
    val state by viewModel.wordResponse.collectAsState()

    var searchQuery by remember { mutableStateOf("") }


    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
    ) {

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search Word ....") },
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = { viewModel.getMeaning(searchQuery) }
                ) { Icon(Icons.Default.Search, contentDescription = "Search") }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is Resource.Success -> {
                val words = (state as Resource.Success).data
                if (!words.isNullOrEmpty()) {

                    WordDetail(words[0])
                }
            }

            is Resource.Error -> {
                Text(
                    text = (state as Resource.Error).message ?: "Error",
                    color = MaterialTheme.colorScheme.error
                )
            }

            null -> {
                Text(
                    "Search for a word to get started!",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }


        }


    }


}

@Composable
fun WordDetail(response: WordResponse) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {

        item {
            Text(
                text = response.word,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            val phoneticText = response.phonetics.firstOrNull { it.text != null }?.text
            if (phoneticText != null) {
                Text(
                    text = phoneticText,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            HorizontalDivider(modifier = Modifier.padding(bottom = 16.dp))
        }

        items(response.meanings) { meaning ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = meaning.partOfSpeech.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFFE91E63),
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    meaning.definitions.forEachIndexed { index, def ->
                        Text(
                            text = "${index + 1}. ${def.definition}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        def.example?.let {
                            Text(
                                text = "Example: \"$it\"",
                                style = MaterialTheme.typography.bodyMedium,
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}