package ru.geekbrains.materialdesignapp.view.pictureOfTheDay

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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
import ru.geekbrains.materialdesignapp.view.SettingsFragment
import ru.geekbrains.materialdesignapp.view.planets.PlanetsFragment
import ru.geekbrains.materialdesignapp.view.recycle.RecycleViewFragment
import ru.geekbrains.materialdesignapp.viewmodel.pictureOfTheDay.PictureOfTheDayViewModel


class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayStartBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private lateinit var explanation: String

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

    private fun setSpansToExplanation() {
        explanation = binding.textView.text.toString()
        if (!explanation.isNullOrBlank()) {
            //            Log.e("--------" , explanation.indexOf("Earth").toString())

            getStartIndex("Earth")
            getEndIndex("Earth")


            val spannable = SpannableString(explanation)


            val string1 = "Earth"
            if (getStartIndex(string1) > -1) {
                spannable.setSpan(
                    ForegroundColorSpan(Color.GREEN),
                    getStartIndex(string1),
                    getEndIndex(string1),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            val string2 = "brighter"
            if (getStartIndex(string2) > -1) {
                spannable.setSpan(
                    StyleSpan(Typeface.BOLD),
                    getStartIndex(string2),
                    getEndIndex(string2),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.setSpan(
                    ForegroundColorSpan(Color.BLACK),
                    getStartIndex(string2),
                    getEndIndex(string2),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            val string3 = "ice"
            if (getStartIndex(string3) > -1) {
                spannable.setSpan(
                    StyleSpan(Typeface.ITALIC),
                    getStartIndex(string3),
                    getEndIndex(string3),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.setSpan(
                    ForegroundColorSpan(Color.BLUE),
                    getStartIndex(string3),
                    getEndIndex(string3),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            binding.textView.text = spannable

        }
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

                    setExplanation(explanation) // добавление описания под фото

                    setExplanationBottomSheet(explanation) // добавление описания для фото в bottomSheet
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

    private fun setExplanationBottomSheet(explanation: String?) {
        val view: ConstraintLayout? = view?.findViewById(R.id.bottom_sheet_container)
        view?.findViewById<TextView>(R.id.bottomSheetDescription)?.text = explanation
    }

    private fun setExplanation(explanation: String?) {
        if (explanation.isNullOrEmpty()) {
            toast("Explanation is empty")
        } else {
            binding.textView.text = explanation

            setSpansToExplanation()
        }
    }

    private fun getStartIndex(word: String) = explanation.indexOf(word)    // получение индекса первой буквы в слове в описании к pod

    private fun getEndIndex(word: String) = explanation.indexOf(word) + word.length   // получение индекса последней буквы в слове в описании к pod

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
