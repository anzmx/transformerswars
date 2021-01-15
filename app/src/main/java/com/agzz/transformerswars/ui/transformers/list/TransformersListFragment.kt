package com.agzz.transformerswars.ui.transformers.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.agzz.transformerswars.databinding.FragmentTransformersListBinding
import com.agzz.transformerswars.networking.Status
import com.agzz.transformerswars.ui.transformers.list.adapter.TransformersListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransformersListFragment : Fragment() {
    private val transformersListViewModel: TransformersListViewModel by viewModels()
    private lateinit var transformersListAdapter: TransformersListAdapter

    private var _binding: FragmentTransformersListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransformersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiElements()
        initViewModelCallbacks()
    }

    private fun initUiElements() {
        transformersListAdapter = TransformersListAdapter { transformer ->
            val builder: AlertDialog.Builder? = activity?.let {
                AlertDialog.Builder(it)
            }
            builder?.apply {
                setTitle("Edit/Delete Transformer")
                setMessage("Do you want to edit or delete this transformer?")
                setPositiveButton(
                    "Delete"
                ) { _, _ ->
                    transformer.id?.let { transformersListViewModel.deleteTransformer(it) }
                }
                setNegativeButton("Cancel", null)
                setNeutralButton("Edit"
                ) { _, _ ->
                    val action = TransformersListFragmentDirections.actionListFragmentToDetailFragment(transformer)
                    findNavController().navigate(action)
                }
            }
            builder?.show()
            true
        }
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.transformersList.addItemDecoration(dividerItemDecoration)
        binding.transformersList.layoutManager = LinearLayoutManager(requireContext())
        binding.transformersList.adapter = transformersListAdapter
        binding.battleButton.setOnClickListener { transformersListViewModel.startWar() }
        binding.addButton.setOnClickListener {
            val action = TransformersListFragmentDirections.actionListFragmentToDetailFragment(null)
            findNavController().navigate(action)
        }
    }

    private fun initViewModelCallbacks() {
        transformersListViewModel.displayTransformersList.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.INVISIBLE
                }
                Status.SUCCESS -> {
                    it.data?.let { data -> transformersListAdapter.items = data.transformers }
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        })

        transformersListViewModel.displayTransformersWar.observe(viewLifecycleOwner, {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Transformers War")
            builder.setMessage(it)
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        })
    }
}