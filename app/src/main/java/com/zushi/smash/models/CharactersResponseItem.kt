package com.zushi.smash.models

data class CharactersResponseItem(
    val alsoAppearsIn: List<String>,
    val availability: String,
    val images: Images,
    val name: String,
    val order: String,
    val series: Series
)