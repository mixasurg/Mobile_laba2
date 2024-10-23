package com.example.laba2

import Miniature
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class MainFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MiniatureAdapter
    private lateinit var dataList: MutableList<Miniature>
    private lateinit var addButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        dataList = loadMiniaturesFromXml()

        adapter = MiniatureAdapter(dataList, object : MiniatureAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val detailFragment = DetailFragment()

                // Передаем данные во фрагмент с использованием Bundle
                val bundle = Bundle()
                bundle.putParcelable("miniatureData", dataList[position])
                detailFragment.arguments = bundle

                // Выполняем транзакцию фрагмента
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }

            override fun onDeleteClick(position: Int) {
                adapter.removeItem(position)
            }
        })

        recyclerView.adapter = adapter

        addButton  = view.findViewById(R.id.fabAdd)
        addButton.setOnClickListener {
            val newId = if (dataList.isNotEmpty()) {
                dataList.maxOf { it.id } + 1
            } else {
                1
            }

            val newMiniature = Miniature(
                id = newId,
                name = "New Name",
                faction = "New Faction",
                imageUrls = listOf("url1", "url2")
            )

            dataList.add(newMiniature)
            adapter.notifyItemInserted(dataList.size - 1)
        }
        return view
    }


    private fun loadMiniaturesFromXml(): MutableList<Miniature> {
        val miniatureList = mutableListOf<Miniature>()

        try {
            val inputStream: InputStream = resources.openRawResource(R.raw.miniatures)
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
                            miniature = Miniature(0,"", "", emptyList())
                            imageUrls = mutableListOf()
                        } else if (miniature != null) {
                            when (tagName) {
                                "name" -> miniature.name = parser.nextText()
                                "faction" -> miniature.faction = parser.nextText()
                                "imageUrl" -> imageUrls.add(parser.nextText())
                                "id" -> miniature.id = parser.nextText().toInt()
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (tagName.equals("miniature", ignoreCase = true)) {
                            miniature?.let {
                                it.imageUrls = imageUrls
                                miniatureList.add(it)
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
        return miniatureList
    }

}
