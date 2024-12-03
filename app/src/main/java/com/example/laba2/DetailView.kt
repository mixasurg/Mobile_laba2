package com.example.laba2

interface DetailView {
    fun displayMiniatureDetails(miniature: Miniature)
    fun navigateToEdit(miniature: Miniature)
    fun navigateBack()
}
