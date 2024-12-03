package com.example.laba2

import android.content.Context
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class MiniatureRepository(requireContext: Context) {
    private val miniatures = mutableListOf<Miniature>()
    private val factions = mutableMapOf<String, String>()
    private val context = requireContext


    fun getMiniatures(): MutableList<Miniature> {
        return miniatures
    }

    fun getFactions(): Map<String, String> {
        return factions
    }

    fun addMiniature(miniature: Miniature) {
        miniatures.add(miniature)
    }

    fun updateMiniature(updatedMiniature: Miniature) {
        val index = miniatures.indexOfFirst { it.id == updatedMiniature.id }
        if (index != -1) {
            miniatures[index] = updatedMiniature
        }
    }

    fun deleteMiniature(miniature: Miniature) {
        miniatures.remove(miniature)
    }

    fun loadMiniaturesFromXml(): MutableList<Miniature> {
        try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.miniatures)
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)

            var eventType = parser.eventType
            var miniature: Miniature? = null
            var imageUrls = mutableListOf<String>()

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name

                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagName.equals("miniature", ignoreCase = true)) {
                            miniature = Miniature(0, "", "", emptyList())
                            imageUrls = mutableListOf()

                        } else if (miniature != null) {
                            when (tagName) {
                                "id" -> miniature.id = parser.nextText().toInt()
                                "name" -> miniature.name = parser.nextText()
                                "faction" -> miniature.faction = parser.nextText()
                                "imageUrl" -> imageUrls.add(parser.nextText())
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (tagName.equals("miniature", ignoreCase = true)) {
                            miniature?.let {
                                it.imageUrls = imageUrls
                                miniatures.add(it)
                            }
                        }
                    }
                }
                eventType = parser.next()
            }
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return miniatures
    }

    fun loadFactionsFromXml(): Map<String, String> {
        try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.factions)
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)

            var eventType = parser.eventType
            var factionName: String? = null

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagName.equals("faction", ignoreCase = true)) {
                            factionName = ""
                        } else if (factionName != null) {
                            when (tagName) {
                                "name" -> factionName = parser.nextText()
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (tagName.equals("faction", ignoreCase = true) && factionName != null) {
                            factions[factionName] = ""
                        }
                    }
                }
                eventType = parser.next()
            }

            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return factions
    }
}
