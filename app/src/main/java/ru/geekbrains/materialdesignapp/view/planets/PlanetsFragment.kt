package ru.geekbrains.materialdesignapp.view.planets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.materialdesignapp.databinding.FragmentPlanetsBinding
import ru.geekbrains.materialdesignapp.viewmodel.ViewPagerAdapter

class PlanetsFragment : Fragment() {
    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        binding.viewPager.adapter = ViewPagerAdapter(parentFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PlanetsFragment()
    }
}