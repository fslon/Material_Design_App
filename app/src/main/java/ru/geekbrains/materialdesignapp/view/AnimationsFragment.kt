package ru.geekbrains.materialdesignapp.view

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
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
        toolbar.bottomAppBar.menu.clear() // скрывает ненужные кнопки на тулбаре

        toolbar.bottomAppBar.setNavigationOnClickListener { binding.drawerLayout.openDrawer(GravityCompat.START) }

        initDrawer()
    }

    private fun initDrawer() {
        toggle = ActionBarDrawerToggle(activity, binding.drawerLayout, R.string.open_drawer_anim, R.string.close_drawer_anim)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setCheckedItem(R.id.fade) // при инициализации подсвечивается элемент (первый) в navigationView

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.fade -> {
                    navigationViewButtonsAction("fade")
                }
                R.id.slide -> {
                    navigationViewButtonsAction("slide")
                }
                R.id.explode -> {
                    navigationViewButtonsAction("explode")
                }

            }
            true

        }

    }

    private fun navigationViewButtonsAction(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
        binding.drawerLayout.closeDrawers()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
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