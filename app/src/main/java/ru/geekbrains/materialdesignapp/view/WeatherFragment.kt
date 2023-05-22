package ru.geekbrains.materialdesignapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geekbrains.materialdesignapp.R
import ru.geekbrains.materialdesignapp.databinding.FragmentMarsBinding
import ru.geekbrains.materialdesignapp.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }
}