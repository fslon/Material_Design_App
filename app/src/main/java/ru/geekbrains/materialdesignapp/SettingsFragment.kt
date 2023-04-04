package ru.geekbrains.materialdesignapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.geekbrains.materialdesignapp.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


// TODO: checkedButton назначить при входе



        binding.radioGroupThemes.check(binding.radioButtonPink.id) // todo сделать под тему

        binding.radioGroupThemes.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, id ->
            when (id) {
                binding.radioButtonLight.id -> {
                    Toast.makeText(requireContext(), "нажато light", Toast.LENGTH_SHORT).show()
                }
                binding.radioButtonDark.id -> {
                    Toast.makeText(requireContext(), "нажато dark ", Toast.LENGTH_SHORT).show()
                }
                binding.radioButtonPink.id -> {
                    Toast.makeText(requireContext(), "нажато pink", Toast.LENGTH_SHORT).show()
                }
                binding.radioButtonGreen.id -> {
                    Toast.makeText(requireContext(), "нажато green", Toast.LENGTH_SHORT).show()
                }

            }
        })


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