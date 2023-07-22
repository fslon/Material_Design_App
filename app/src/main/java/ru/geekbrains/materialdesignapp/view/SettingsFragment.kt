package ru.geekbrains.materialdesignapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.materialdesignapp.R
import ru.geekbrains.materialdesignapp.databinding.FragmentSettingsBinding
import ru.geekbrains.materialdesignapp.viewmodel.ThemeViewModel


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ThemeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ThemeViewModel::class.java)
        observeViewModel()
        setupRadioGroup()

// TODO: checkedButton назначить при входе


//        binding.radioGroupThemes.check(binding.radioButtonPink.id) // todo сделать под тему

//        binding.radioGroupThemes.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, id ->
//            when (id) {
//                binding.radioButtonLight.id -> {    // 0
//                    Toast.makeText(requireContext(), "нажато light", Toast.LENGTH_SHORT).show()
//
//                }
//                binding.radioButtonDark.id -> {    // 1
//                    Toast.makeText(requireContext(), "нажато dark ", Toast.LENGTH_SHORT).show()
//
//                }
//                binding.radioButtonPink.id -> {    // 2
//                    Toast.makeText(requireContext(), "нажато pink", Toast.LENGTH_SHORT).show()
//                }
//                binding.radioButtonGreen.id -> {    // 3
//                    Toast.makeText(requireContext(), "нажато green", Toast.LENGTH_SHORT).show()
//                }
//
//            }
//        })


    }

    private fun observeViewModel() {
        val selectedTheme = viewModel.getSelectedTheme()
        when (selectedTheme) {
            R.style.Theme_MaterialDesignApp -> binding.radioButtonLight.isChecked = true
            R.style.DarkTheme -> binding.radioButtonDark.isChecked = true
            R.style.PinkTheme -> binding.radioButtonPink.isChecked = true
            R.style.GreenTheme -> binding.radioButtonGreen.isChecked = true
        }
    }

    private fun setupRadioGroup() {
        binding.radioGroupThemes.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioButtonLight.id -> viewModel.saveSelectedTheme(R.style.Theme_MaterialDesignApp)
                binding.radioButtonDark.id -> viewModel.saveSelectedTheme(R.style.DarkTheme)
                binding.radioButtonPink.id -> viewModel.saveSelectedTheme(R.style.PinkTheme)
                binding.radioButtonGreen.id -> viewModel.saveSelectedTheme(R.style.GreenTheme)
            }
            activity?.recreate()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}