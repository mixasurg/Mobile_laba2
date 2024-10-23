package com.example.laba2

import Miniature
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class EditFragment : Fragment() {
    private lateinit var miniature: Miniature
    private lateinit var factionList: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_miniature, container, false)

        miniature = arguments?.getParcelable("miniatureData") ?: Miniature(0, "", "", emptyList())

        factionList = loadFactionsFromXml().keys.toList()
        Log.d("EditFragment", "Loaded factions: $factionList")

        val nameEdit: EditText = view.findViewById(R.id.nameEdit)
        val factionSpinner: Spinner = view.findViewById(R.id.factionSpinner)
        val saveButton: Button = view.findViewById(R.id.saveButton)
        val backButton: FloatingActionButton = view.findViewById(R.id.backButton)

        nameEdit.setText(miniature.name)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, factionList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        factionSpinner.adapter = adapter

        val factionIndex = factionList.indexOf(miniature.faction)
        if (factionIndex >= 0) {
            factionSpinner.setSelection(factionIndex)
        }

        saveButton.setOnClickListener {
            miniature.name = nameEdit.text.toString()
            miniature.faction = factionSpinner.selectedItem.toString()

            val resultBundle = Bundle()
            resultBundle.putParcelable("updatedMiniature", miniature)

            parentFragmentManager.setFragmentResult("requestKey", resultBundle)

            requireActivity().supportFragmentManager.popBackStack()
        }

        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }

    private fun loadFactionsFromXml(): Map<String, String> {
        val factionMap = mutableMapOf<String, String>()
        try {
            val inputStream: InputStream = resources.openRawResource(R.raw.factions)
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
                            factionMap[factionName] = ""
                        }
                    }
                }
                eventType = parser.next()
            }

            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("EditFragment", "Error loading factions: ${e.message}")
        }
        return factionMap
    }
}
