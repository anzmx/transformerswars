package com.agzz.transformerswars.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agzz.transformerswars.data.local.prefs.SharedPreferencesManager
import com.agzz.transformerswars.databinding.FragmentWelcomeBinding
import com.agzz.transformerswars.networking.Status
import com.agzz.transformerswars.ui.transformers.detail.CreateEditFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment: Fragment() {

    private val welcomeViewModel: WelcomeViewModel by viewModels()

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiElements()
        initViewModelCallbacks()
    }

    private fun initUiElements() {
        binding.startBtn.setOnClickListener {
            welcomeViewModel.getAllSpark()
        }
    }

    private fun initViewModelCallbacks() {
        welcomeViewModel.resultAllSpark.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.INVISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val action = WelcomeFragmentDirections.actionWelcomefragmentToListFragment()
                    findNavController().navigate(action)
                }
            }
        })
    }
}