package ru.geekbrains.materialdesignapp.view

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import ru.geekbrains.materialdesignapp.R
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

        binding.fadeLayout.fadeButton.setOnClickListener { // действие при нажатие на кнопку в fade контейнере (стартовом)
            TransitionManager.beginDelayedTransition(binding.transitionsContainer)
            textIsVisible = !textIsVisible
            binding.fadeLayout.fadeText.visibility = if (textIsVisible) View.VISIBLE else View.GONE
        }

        initToolbarAndDrawer()

    }


    private fun initToolbarAndDrawer() {
        val toolbar = binding.toolbar
        toolbar.bottomAppBar.menu.clear() // скрывает ненужные кнопки на тулбаре

        toolbar.bottomAppBar.setNavigationOnClickListener { binding.drawerLayout.openDrawer(GravityCompat.START) } // кнопка гамбургер на тулбаре

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
                    navigationViewButtonsAction("fade") // действие с navigationView при нажатии

                    binding.animationsContainer.removeAllViews()

                    val fadeLayout: ConstraintLayout = layoutInflater.inflate(R.layout.transition_fade_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        fadeLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    fadeLayout.getViewById(R.id.fade_button).setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.animationsContainer)
                        textIsVisible = !textIsVisible
                        fadeLayout.getViewById(R.id.slide_text).visibility = if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }
                R.id.slide -> {
                    navigationViewButtonsAction("slide")

                    binding.animationsContainer.removeAllViews()

                    val slideLayout: ConstraintLayout = layoutInflater.inflate(R.layout.transition_slide_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        slideLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    slideLayout.getViewById(R.id.slide_button).setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.animationsContainer)
                        textIsVisible = !textIsVisible
                        slideLayout.getViewById(R.id.slide_text).visibility = if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }
                R.id.explode -> {
                    navigationViewButtonsAction("explode")

                    binding.animationsContainer.removeAllViews()

                    val explodeLayout: ConstraintLayout = layoutInflater.inflate(R.layout.transition_explode_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        explodeLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    explodeLayout.getViewById(R.id.explode_button).setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.animationsContainer)
                        textIsVisible = !textIsVisible
                        explodeLayout.getViewById(R.id.explode_text).visibility = if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }

                R.id.increase -> {
                    navigationViewButtonsAction("increase")

                    binding.animationsContainer.removeAllViews()

                    val increaseLayout: ConstraintLayout = layoutInflater.inflate(R.layout.transition_increase_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        increaseLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    increaseLayout.getViewById(R.id.increase_button).setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.animationsContainer)
                        textIsVisible = !textIsVisible
                        increaseLayout.getViewById(R.id.increase_text).visibility = if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }

                R.id.trajectory_movement -> {
                    navigationViewButtonsAction("trajectory_movement")

                    binding.animationsContainer.removeAllViews()

                    val trajectoryMovementLayout: ConstraintLayout =
                        layoutInflater.inflate(R.layout.transition_trajectory_movement_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        trajectoryMovementLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    trajectoryMovementLayout.getViewById(R.id.trajectory_movement_button).setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.animationsContainer)
                        textIsVisible = !textIsVisible
                        trajectoryMovementLayout.getViewById(R.id.trajectory_movement_text).visibility =
                            if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }

                R.id.shuffle_objects -> {
                    navigationViewButtonsAction("shuffle_objects")

                    binding.animationsContainer.removeAllViews()

                    val transitionShuffleObjectsLayout: ConstraintLayout =
                        layoutInflater.inflate(R.layout.transition_shuffle_objects_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        transitionShuffleObjectsLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    transitionShuffleObjectsLayout.getViewById(R.id.shuffle_objects_button).setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.animationsContainer)
                        textIsVisible = !textIsVisible
                        transitionShuffleObjectsLayout.getViewById(R.id.shuffle_objects_text).visibility =
                            if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }

                R.id.object_animator_for_drawer -> {
                    navigationViewButtonsAction("object_animator")

                    binding.animationsContainer.removeAllViews()

                    val objectAnimatorForDrawer: ConstraintLayout =
                        layoutInflater.inflate(R.layout.transition_object_animator_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        objectAnimatorForDrawer,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    objectAnimatorForDrawer.getViewById(R.id.object_animator_for_drawer_button).setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.animationsContainer)
                        textIsVisible = !textIsVisible
                        objectAnimatorForDrawer.getViewById(R.id.object_animator_for_drawer_text).visibility =
                            if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }

                R.id.state_list_animator_for_drawer -> {
                    navigationViewButtonsAction("state_list_animator_for_drawer")

                    binding.animationsContainer.removeAllViews()

                    val stateListAnimatorForDrawer: ConstraintLayout =
                        layoutInflater.inflate(R.layout.transition_state_list_animator_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        stateListAnimatorForDrawer,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    stateListAnimatorForDrawer.getViewById(R.id.state_list_animator_button).setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.animationsContainer)
                        textIsVisible = !textIsVisible
                        stateListAnimatorForDrawer.getViewById(R.id.state_list_animator_text).visibility =
                            if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }

                R.id.constraint_layout_and_constraint_set -> {
                    navigationViewButtonsAction("constraint_layout_and_constraint_set")

                    binding.animationsContainer.removeAllViews()

                    val transitionConstraintSetLayout: ConstraintLayout =
                        layoutInflater.inflate(R.layout.transition_constraint_set_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        transitionConstraintSetLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    transitionConstraintSetLayout.getViewById(R.id.constraint_layout_and_constraint_set_button).setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.animationsContainer)
                        textIsVisible = !textIsVisible
                        transitionConstraintSetLayout.getViewById(R.id.constraint_layout_and_constraint_set_text).visibility =
                            if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }

                R.id.additional_possibilities -> {
                    navigationViewButtonsAction("additional_possibilities")

                    binding.animationsContainer.removeAllViews()

                    val transitionAdditionalPossibilitiesLayout: ConstraintLayout =
                        layoutInflater.inflate(R.layout.transition_additional_possibilities_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        transitionAdditionalPossibilitiesLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    transitionAdditionalPossibilitiesLayout.getViewById(R.id.additional_possibilities_button).setOnClickListener {
                        TransitionManager.beginDelayedTransition(binding.animationsContainer)
                        textIsVisible = !textIsVisible
                        transitionAdditionalPossibilitiesLayout.getViewById(R.id.additional_possibilities_text).visibility =
                            if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }


            }
            true

        }

    }

    private fun navigationViewButtonsAction(text: String) {   // при нажатии кнопки в navigationView
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