package com.example.laba2

class DetailPresenter(private val view: DetailView) {
    fun loadMiniatureDetails(miniature: Miniature) {
        view.displayMiniatureDetails(miniature)
    }

    fun onEditMiniature(miniature: Miniature) {
        view.navigateToEdit(miniature)
    }
}
