package ru.geekbrains.materialdesignapp.view

import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import ru.geekbrains.materialdesignapp.R
import ru.geekbrains.materialdesignapp.databinding.BottomAppBarBinding
import ru.geekbrains.materialdesignapp.databinding.FragmentAnimationsBinding


class AnimationsFragment : Fragment() {
    private var _binding: FragmentAnimationsBinding? = null
    private val binding get() = _binding!!

    private var textIsVisible = false

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAnimationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.transitionsContainer)
            textIsVisible = !textIsVisible
            binding.text.visibility = if (textIsVisible) View.VISIBLE else View.GONE
        }

        initToolbarAndDrawer()
    }


    private fun initToolbarAndDrawer() {
        val toolbar = binding.toolbar
//        setSupportActionBar(toolbar)
        initDrawer(toolbar)
    }

    private fun initDrawer(toolbar: BottomAppBarBinding) {
        toggle = ActionBarDrawerToggle(activity, binding.drawerLayout, R.string.open_drawer_anim, R.string.close_drawer_anim )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.first -> {
                    Toast.makeText(requireContext(), "шоуашшу", Toast.LENGTH_SHORT).show()
                    Log.e("аыуаы", "initDrawer:11111111" )
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = AnimationsFragment()
    }
}