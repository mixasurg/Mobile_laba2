package com.example.laba2

import android.util.Log

class EditPresenter(private val repository: MiniatureRepository, private val view: EditView) {
    fun loadMiniatureForEdit(miniature: Miniature) {
        repository.loadFactionsFromXml()
        val factions = repository.getFactions()
        view.populateFields(miniature, factions)
    }

    fun saveMiniature(miniature: Miniature) {
        try {
            repository.updateMiniature(miniature)
            view.navigateBack()
        } catch (e: Exception) {
            view.showError("Ошибка сохранения")
        }
    }
}
