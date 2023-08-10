package ru.geekbrains.materialdesignapp.view.pictureOfTheDay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.geekbrains.materialdesignapp.R
import ru.geekbrains.materialdesignapp.databinding.FragmentPictureOfTheDayStartBinding
import ru.geekbrains.materialdesignapp.model.pictureOfTheDay.PictureOfTheDayData
import ru.geekbrains.materialdesignapp.view.AnimationsFragment
import ru.geekbrains.materialdesignapp.view.MainActivity
import ru.geekbrains.materialdesignapp.view.recycle.RecycleViewFragment
import ru.geekbrains.materialdesignapp.view.SettingsFragment
import ru.geekbrains.materialdesignapp.view.planets.PlanetsFragment
import ru.geekbrains.materialdesignapp.viewmodel.pictureOfTheDay.PictureOfTheDayViewModel


class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayStartBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData()
            .observe(viewLifecycleOwner) { renderData(it) }
        _binding = FragmentPictureOfTheDayStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

//        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setBottomAppBar(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu -> {
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, SettingsFragment())?.addToBackStack("")
                    ?.commit()
//                activity.supportFragmentManager.beginTransaction().replace(R.id.container, settingsFragment).commitAllowingStateLoss()
            }
            R.id.planets_menu -> {
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, PlanetsFragment())?.addToBackStack("")
                    ?.commit()
            }
            R.id.animations_menu -> {
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, AnimationsFragment())?.addToBackStack("")
                    ?.commit()
            }
            R.id.recycler_view_menu -> {
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, RecycleViewFragment())?.addToBackStack("")
                    ?.commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
    }


    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val explanation = serverResponseData.explanation

                if (url.isNullOrEmpty()) {
                    toast("Link is empty")//Отобразите ошибку
                    // showError("Сообщение, что ссылка пустая")

                } else {

                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                        crossfade(true)
                    }

                    setExplanation(explanation) // добавление описания для фото в bottomSheet
                }
            }
            is PictureOfTheDayData.Loading -> {
//Отобразите загрузку
//showLoading()
            }
            is PictureOfTheDayData.Error -> {
//Отобразите ошибку
//showError(data.error.message)
                toast(data.error.message)
            }
        }

    }

    private fun setExplanation(explanation: String?) {
        val view: ConstraintLayout? = view?.findViewById(R.id.bottom_sheet_container)
        view?.findViewById<TextView>(R.id.bottomSheetDescription)?.text = explanation
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }


    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}
