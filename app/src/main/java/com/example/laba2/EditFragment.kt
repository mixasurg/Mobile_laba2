package com.example.laba2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.laba2.databinding.FragmentEditMiniatureBinding

class EditFragment : Fragment(), EditView {
    private var _binding: FragmentEditMiniatureBinding? = null
    private val binding get() = _binding!!

    private val args: EditFragmentArgs by navArgs() // Используем Safe Args для получения данных
    private lateinit var presenter: EditPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditMiniatureBinding.inflate(inflater, container, false)
        presenter = EditPresenter(MiniatureRepository(requireContext()), this)

        val miniature = args.miniatureData // Получаем объект Miniature через Safe Args
        presenter.loadMiniatureForEdit(miniature)

        binding.saveButton.setOnClickListener {
            miniature.name = binding.nameEdit.text.toString()
            miniature.faction = binding.factionSpinner.selectedItem.toString()
            presenter.saveMiniature(miniature)
        }

        binding.backButton.setOnClickListener{
            navigateBack()
        }

        return binding.root
    }


    override fun populateFields(miniature: Miniature, factions: Map<String, String>) {
        binding.nameEdit.setText(miniature.name)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, factions.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.factionSpinner.adapter = adapter

        val factionIndex = factions.keys.toList().indexOf(miniature.faction)
        if (factionIndex >= 0) {
            binding.factionSpinner.setSelection(factionIndex)
        }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


