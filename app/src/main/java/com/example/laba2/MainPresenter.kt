package com.example.laba2


class MainPresenter(public val repository: MiniatureRepository, private val view: MainView) {
    fun loadMiniatures() {
        try {
            val miniatures = repository.getMiniatures()
            view.showMiniatures(miniatures)
        } catch (e: Exception) {
            view.showError("Failed to load miniatures")
        }
    }

    fun onMiniatureSelected(miniature: Miniature) {
        view.navigateToDetail(miniature)
    }

    fun onAddMiniature() {
        val newMiniature = Miniature(0, "New Miniature", "New Faction", emptyList())
        repository.addMiniature(newMiniature)
        loadMiniatures()
    }

    fun onDeleteMiniature(miniature: Miniature) {
        repository.deleteMiniature(miniature)
        loadMiniatures()
    }

}