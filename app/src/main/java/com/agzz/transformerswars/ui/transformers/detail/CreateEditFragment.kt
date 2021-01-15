package com.agzz.transformerswars.ui.transformers.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.agzz.transformerswars.R
import com.agzz.transformerswars.databinding.FragmentTransformersCreateBinding
import com.agzz.transformerswars.models.Transformer
import com.agzz.transformerswars.networking.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateEditFragment: Fragment() {

    private val createEditTransformersViewModel: CreateEditViewModel by viewModels()
    private val args: CreateEditFragmentArgs by navArgs()

    private var _binding: FragmentTransformersCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransformersCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiElements()
        initViewModelCallbacks()
    }

    private fun initUiElements() {
        val spinner: Spinner = binding.teamsSpn
        ArrayAdapter.createFromResource(requireContext(),
        R.array.teams_array,
        android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }

        args.transformer?.let {
            binding.tvTitle.text = getString(R.string.edit_transformer)
            binding.etName.setText(it.name)
            if (it.team == "A") binding.teamsSpn.setSelection(0) else binding.teamsSpn.setSelection(1)
            binding.numStr.progress = it.strength
            binding.numInt.progress = it.intelligence
            binding.numSpd.progress = it.speed
            binding.numEnd.progress = it.endurance
            binding.numRnk.progress = it.rank
            binding.numCrg.progress = it.courage
            binding.numFpw.progress = it.firepower
            binding.numSkl.progress = it.skill
        }

        binding.btnSave.setOnClickListener {
            val saveTransformer = Transformer(args.transformer?.id,
                binding.etName.text.toString(),
                binding.teamsSpn.selectedItem.toString().first().toString(),
            binding.numStr.progress + 1,
            binding.numInt.progress + 1,
            binding.numSpd.progress + 1,
            binding.numEnd.progress + 1,
            binding.numRnk.progress + 1,
            binding.numCrg.progress + 1,
            binding.numFpw.progress + 1,
            binding.numSkl.progress + 1,
            null)

            args.transformer?.let {
                createEditTransformersViewModel.updateTransformer(saveTransformer)
            } ?: kotlin.run {
                createEditTransformersViewModel.createTransformer(saveTransformer)
            }

        }
    }

    private fun initViewModelCallbacks() {
        createEditTransformersViewModel.resultCreateEdit.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.INVISIBLE
                }
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.INVISIBLE
                    val action = CreateEditFragmentDirections.actionDetailFragmentToListFragment()
                    findNavController().navigate(action)
                }
            }
        })
    }
}