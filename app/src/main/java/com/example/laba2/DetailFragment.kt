package com.example.laba2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.laba2.databinding.FragmentDetailBinding
import androidx.navigation.fragment.findNavController

class DetailFragment : Fragment(), DetailView {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs() // Используем Safe Args для получения данных
    private lateinit var presenter: DetailPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        presenter = DetailPresenter(this)

        val miniature = args.miniatureData // Получаем объект Miniature через Safe Args
        presenter.loadMiniatureDetails(miniature)

        binding.editButton.setOnClickListener {
            presenter.onEditMiniature(miniature)
        }
        binding.backButton.setOnClickListener{
            navigateBack()
        }

        return binding.root
    }

    override fun displayMiniatureDetails(miniature: Miniature) {
        binding.detailName.text = miniature.name
        binding.detailFaction.text = miniature.faction

        val sliderAdapter = SliderAdapter(miniature.imageUrls)
        binding.viewPager.adapter = sliderAdapter
    }

    override fun navigateToEdit(miniature: Miniature) {
        val action = DetailFragmentDirections.actionDetailFragmentToEditFragment(miniature)
        findNavController().navigate(action) // Используем Safe Args для перехода
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun navigateBack() {
        parentFragmentManager.popBackStack()
    }
}



