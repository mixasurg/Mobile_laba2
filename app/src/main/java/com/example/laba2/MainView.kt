package com.example.laba2
interface MainView {
    fun showMiniatures(miniatures: MutableList<Miniature>)
    fun showError(message: String)
    fun navigateToDetail(miniature: Miniature)
    fun navigateToEdit(miniature: Miniature)
}
