package com.example.laba2

import android.os.Bundle
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import Miniature
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailFragment : Fragment() {
    private lateinit var miniature: Miniature
    private lateinit var viewPager: ViewPager2
    private lateinit var detailName: TextView
    private lateinit var detailFaction: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        miniature = arguments?.getParcelable("miniatureData") ?: Miniature(0, "", "", emptyList())

        viewPager = view.findViewById(R.id.viewPager)
        detailName = view.findViewById(R.id.detailName)
        detailFaction = view.findViewById(R.id.detailFaction)
        val backButton: FloatingActionButton = view.findViewById(R.id.backButton)
        val editButton: FloatingActionButton = view.findViewById(R.id.editButton)

        val sliderAdapter = SliderAdapter(miniature.imageUrls)
        viewPager.adapter = sliderAdapter

        detailName.text = miniature.name
        detailFaction.text = miniature.faction


        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        editButton.setOnClickListener {
            val bundle = Bundle()

            val editFragment = EditFragment()
            bundle.putParcelable("miniatureData", miniature)
            editFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, editFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }

}
