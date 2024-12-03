package com.example.laba2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laba2.databinding.FragmentMainBinding

class MainFragment : Fragment(), MainView {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MiniatureAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        presenter = MainPresenter(MiniatureRepository(requireContext()), this)
        presenter.repository.loadMiniaturesFromXml()
        presenter.loadMiniatures()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.fabAdd.setOnClickListener {
            presenter.onAddMiniature()
        }


        return binding.root
    }

    override fun showMiniatures(miniatures: MutableList<Miniature>) {

        adapter = MiniatureAdapter(miniatures, object : MiniatureAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                presenter.onMiniatureSelected(miniatures[position])
            }

            override fun onDeleteClick(position: Int) {
                presenter.onDeleteMiniature(miniatures[position])
            }
        })
        binding.recyclerView.adapter = adapter
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ResourceType")
    override fun navigateToDetail(miniature: Miniature) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(miniature)
        findNavController().navigate(action)
    }


    override fun navigateToEdit(miniature: Miniature) {
        val editFragment = EditFragment()
        editFragment.arguments = Bundle().apply {
            putParcelable("miniatureData", miniature)
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, editFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}