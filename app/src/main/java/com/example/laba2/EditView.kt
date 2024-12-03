package com.example.laba2
interface EditView {
    fun populateFields(miniature: Miniature, factions: Map<String, String>)
    fun showError(message: String)
    fun navigateBack()
}
