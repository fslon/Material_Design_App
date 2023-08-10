package ru.geekbrains.materialdesignapp.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Bundle
import android.transition.*
import android.view.*
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.geekbrains.materialdesignapp.R
import ru.geekbrains.materialdesignapp.databinding.FragmentAnimationsBinding


class AnimationsFragment : Fragment() {

    private val rotation = "rotation" // for object animator
    private val translationY = "translationY" // for object animator
    private val rotationFrom = 0f // for object animator
    private val rotationTo = 225f // for object animator
    private val translationOneContainer = -130f // for object animator
    private val translationTwoContainer = -250f // for object animator
    private val translationPlusImageFrom = 0f // for object animator
    private val translationPlusImageTo = -180f // for object animator
    private val translationZero = 0f // for object animator
    private var isExpanded = false

    lateinit var containerObjectAnimator: FrameLayout
    lateinit var transparentBackgroundObjectAnimator: FrameLayout
    lateinit var optionOneContainerObjectAnimator: LinearLayout
    lateinit var optionTwoContainerObjectAnimator: LinearLayout
    lateinit var fabObjectAnimator: FloatingActionButton
    lateinit var plusImageViewObjectAnimator: ImageView
    lateinit var scrollViewObjectAnimator: NestedScrollView
    lateinit var toolbarObjectAnimator: FrameLayout

    lateinit var containerConstraintSet : ConstraintLayout
    lateinit var backgroundImageConstraintSet : ImageView
    private var show = false

    private var _binding: FragmentAnimationsBinding? = null
    private val binding get() = _binding!!

    private var textIsVisible = false

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var explodeLayout: ConstraintLayout
    lateinit var recyclerViewExplodeLayout: RecyclerView

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
                        fadeLayout.getViewById(R.id.fade_text).visibility = if (textIsVisible) View.VISIBLE else View.GONE
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
                        TransitionManager.beginDelayedTransition(binding.transitionsContainer, Slide(Gravity.END))
                        textIsVisible = !textIsVisible
                        slideLayout.getViewById(R.id.slide_text).visibility = if (textIsVisible) View.VISIBLE else View.GONE
                    }
                }
                R.id.explode -> {
                    navigationViewButtonsAction("explode")

                    binding.animationsContainer.removeAllViews()

                    explodeLayout = layoutInflater.inflate(R.layout.transition_explode_layout, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        explodeLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    recyclerViewExplodeLayout = explodeLayout.getViewById(R.id.recycler_view_explode) as RecyclerView
                    recyclerViewExplodeLayout.adapter = Adapter()

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

                    val frameLayoutIncrease = increaseLayout.getViewById(R.id.transition_increase_container) as FrameLayout
                    val imageViewIncreaseLayout = frameLayoutIncrease.findViewById<ImageView>(R.id.transition_increase_image_view)
                    var isExpanded = false

                    imageViewIncreaseLayout.setOnClickListener {
                        isExpanded = !isExpanded
                        TransitionManager.beginDelayedTransition(
                            frameLayoutIncrease, TransitionSet()
                                .addTransition(ChangeBounds())
                                .addTransition(ChangeImageTransform())
                        )
                        val params: ViewGroup.LayoutParams = imageViewIncreaseLayout.layoutParams
                        params.height =
                            if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        imageViewIncreaseLayout.layoutParams = params
                        imageViewIncreaseLayout.scaleType = if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
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


                    var toRightAnimation = false
                    val containerTrajectory = trajectoryMovementLayout.getViewById(R.id.trajectory_movement_container) as ConstraintLayout
                    val frameLayoutTrajectory = containerTrajectory.findViewById(R.id.trajectory_movement_frame_layout) as FrameLayout
                    val buttonIncreaseLayout = frameLayoutTrajectory.findViewById<Button>(R.id.trajectory_movement_button)

                    buttonIncreaseLayout.setOnClickListener {
                        val changeBounds = ChangeBounds()
                        changeBounds.setPathMotion(ArcMotion())
                        changeBounds.duration = 500
                        TransitionManager.beginDelayedTransition(
                            binding.transitionsContainer,
                            changeBounds
                        )
                        toRightAnimation = !toRightAnimation
                        val params = buttonIncreaseLayout.layoutParams as FrameLayout.LayoutParams
                        params.gravity =
                            if (toRightAnimation) Gravity.END or Gravity.BOTTOM else Gravity.START or Gravity.TOP
                        buttonIncreaseLayout.layoutParams = params
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

                    val containerShuffle = transitionShuffleObjectsLayout.getViewById(R.id.transition_shuffle_objects_container) as ConstraintLayout
                    val parentLinearLayoutShuffle =
                        containerShuffle.findViewById(R.id.transition_shuffle_objects_linear_layout_parent) as LinearLayout
                    val childLinearLayoutShuffle = containerShuffle.findViewById(R.id.transition_shuffle_objects_linear_layout_child) as LinearLayout
                    val buttonShuffle = parentLinearLayoutShuffle.findViewById<Button>(R.id.transition_shuffle_objects_button)

                    val titles: MutableList<String> = ArrayList()
                    for (i in 0..4) {
                        titles.add(String.format("Item %d", i + 1))
                    }
                    createViews(childLinearLayoutShuffle, titles)
                    buttonShuffle.setOnClickListener {
                        TransitionManager.beginDelayedTransition(
                            childLinearLayoutShuffle,
                            ChangeBounds()
                        )
                        titles.shuffle()
                        createViews(childLinearLayoutShuffle, titles)
                    }

                }

                R.id.object_animator_for_drawer -> { // stateListAnimator добавлен сюда
                    navigationViewButtonsAction("object_animator")

                    binding.animationsContainer.removeAllViews()

                    val objectAnimatorLayout: FrameLayout =
                        layoutInflater.inflate(R.layout.transition_object_animator_layout, null) as FrameLayout
                    binding.animationsContainer.addView(
                        objectAnimatorLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    containerObjectAnimator = objectAnimatorLayout.findViewById(R.id.object_animator_container) as FrameLayout
                    transparentBackgroundObjectAnimator =
                        containerObjectAnimator.findViewById(R.id.object_animator_transparent_background) as FrameLayout
                    optionOneContainerObjectAnimator =
                        containerObjectAnimator.findViewById(R.id.object_animator_option_one_container) as LinearLayout
                    optionTwoContainerObjectAnimator =
                        containerObjectAnimator.findViewById(R.id.object_animator_option_two_container) as LinearLayout
                    fabObjectAnimator = containerObjectAnimator.findViewById(R.id.object_animator_fab) as FloatingActionButton
                    plusImageViewObjectAnimator = containerObjectAnimator.findViewById(R.id.object_animator_plus_imageview) as ImageView
                    scrollViewObjectAnimator = containerObjectAnimator.findViewById(R.id.object_animator_scroll_view) as NestedScrollView
                    toolbarObjectAnimator = containerObjectAnimator.findViewById(R.id.object_animator_toolbar) as FrameLayout

                    setFAB()

                    scrollViewObjectAnimator.setOnScrollChangeListener { _, _, _, _, _ -> // появляется граница тулбара после скролла
                        toolbarObjectAnimator.isSelected =
                            scrollViewObjectAnimator.canScrollVertically(-1)
                    }
                }

//                R.id.state_list_animator_for_drawer -> {
//                    navigationViewButtonsAction("state_list_animator_for_drawer")
//
//                    binding.animationsContainer.removeAllViews()
//
//                    val stateListAnimatorForDrawer: ConstraintLayout =
//                        layoutInflater.inflate(R.layout.transition_state_list_animator_layout, null) as ConstraintLayout
//                    binding.animationsContainer.addView(
//                        stateListAnimatorForDrawer,
//                        ConstraintLayout.LayoutParams.MATCH_PARENT,
//                        ConstraintLayout.LayoutParams.MATCH_PARENT
//                    )
//
//                }

                R.id.constraint_layout_and_constraint_set -> {
                    navigationViewButtonsAction("constraint_layout_and_constraint_set")

                    binding.animationsContainer.removeAllViews()

                    val transitionConstraintSetLayout: ConstraintLayout =
                        layoutInflater.inflate(R.layout.transition_constraint_set_layout_start, null) as ConstraintLayout
                    binding.animationsContainer.addView(
                        transitionConstraintSetLayout,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )

                    containerConstraintSet = transitionConstraintSetLayout.findViewById(R.id.constraint_layout_constraint_container) as ConstraintLayout
                    backgroundImageConstraintSet =
                        containerConstraintSet.findViewById(R.id.constraint_layout_backgroundImage) as ImageView

                    backgroundImageConstraintSet.setOnClickListener {
                        if (show) hideComponents() else
                            showComponents()
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

    private fun explode(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.duration = 1000

        val set = TransitionSet()
            .addTransition(explode)
            .addTransition(Fade().addTarget(clickedView))
            .addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    transition.removeListener(this)
//                    requireActivity().onBackPressed()
                }
            })

        TransitionManager.beginDelayedTransition(recyclerViewExplodeLayout, set)
        recyclerViewExplodeLayout.adapter = null
    }

    private fun createViews(layout: ViewGroup, titles: List<String>) {
        layout.removeAllViews()
        for (title in titles) {
            val textView = TextView(requireContext())
            textView.text = title
            textView.gravity = Gravity.CENTER_HORIZONTAL
            ViewCompat.setTransitionName(textView, title)
            layout.addView(textView)
        }
    }

    private fun setFAB() {
        setInitialState()
        fabObjectAnimator.setOnClickListener {
            if (isExpanded) {
                collapseFab()
            } else {
                expandFAB()
            }
        }
    }

    private fun setInitialState() {
        transparentBackgroundObjectAnimator.apply {
            alpha = 0f
        }
        optionTwoContainerObjectAnimator.apply {
            alpha = 0f
            isClickable = false
        }
        optionOneContainerObjectAnimator.apply {
            alpha = 0f
            isClickable = false
        }
    }

    private fun expandFAB() {
        isExpanded = true
        ObjectAnimator.ofFloat(
            plusImageViewObjectAnimator, rotation, rotationFrom,
            rotationTo
        ).start()
        ObjectAnimator.ofFloat(
            optionTwoContainerObjectAnimator, translationY,
            translationTwoContainer
        ).start()
        ObjectAnimator.ofFloat(
            optionOneContainerObjectAnimator, translationY,
            translationOneContainer
        ).start()
        optionTwoContainerObjectAnimator.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    optionTwoContainerObjectAnimator.isClickable = true
                    optionTwoContainerObjectAnimator.setOnClickListener {
                        Toast.makeText(
                            requireActivity(), "Option 2",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        optionOneContainerObjectAnimator.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    optionOneContainerObjectAnimator.isClickable = true
                    optionOneContainerObjectAnimator.setOnClickListener {
                        Toast.makeText(
                            requireActivity(), "Option 1",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        transparentBackgroundObjectAnimator.animate()
            .alpha(0.9f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    transparentBackgroundObjectAnimator.isClickable = true
                }
            })
    }

    private fun collapseFab() {
        isExpanded = false
        ObjectAnimator.ofFloat(
            plusImageViewObjectAnimator, rotation,
            translationPlusImageFrom, translationPlusImageTo
        ).start()
        ObjectAnimator.ofFloat(
            optionTwoContainerObjectAnimator, translationY,
            translationZero
        ).start()
        ObjectAnimator.ofFloat(
            optionOneContainerObjectAnimator, translationY,
            translationZero
        ).start()
        optionTwoContainerObjectAnimator.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    optionTwoContainerObjectAnimator.isClickable = false
                    optionOneContainerObjectAnimator.setOnClickListener(null)
                }
            })
        optionOneContainerObjectAnimator.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    optionOneContainerObjectAnimator.isClickable = false
                }
            })
        transparentBackgroundObjectAnimator.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    transparentBackgroundObjectAnimator.isClickable = false
                }
            })
    }

    private fun showComponents() {
        show = true
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.transition_constraint_set_layout_end)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200
        TransitionManager.beginDelayedTransition(
            containerConstraintSet,
            transition
        )
        constraintSet.applyTo(containerConstraintSet)
    }


    private fun hideComponents() {
        show = false
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.transition_constraint_set_layout_start)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200
        TransitionManager.beginDelayedTransition(
            containerConstraintSet,
            transition
        )
        constraintSet.applyTo(containerConstraintSet)
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

    private inner class Adapter : RecyclerView.Adapter<ViewHolder>() { // для explode animation
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.transition_explode_recycle_view_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                explode(it)
            }
        }

        override fun getItemCount(): Int {
            return 32
        }
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}


