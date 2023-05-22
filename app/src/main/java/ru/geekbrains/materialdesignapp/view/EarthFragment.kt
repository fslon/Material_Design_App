package ru.geekbrains.materialdesignapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.materialdesignapp.databinding.FragmentEarthBinding
import ru.geekbrains.materialdesignapp.databinding.FragmentPictureOfTheDayBinding

class EarthFragment : Fragment() {
    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = EarthFragment()
    }
}