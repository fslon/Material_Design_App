package ru.geekbrains.materialdesignapp.view.planets

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.geekbrains.materialdesignapp.R
import ru.geekbrains.materialdesignapp.databinding.FragmentEarthBinding
import ru.geekbrains.materialdesignapp.model.pictureOfEarth.PictureOfEarthData
import ru.geekbrains.materialdesignapp.viewmodel.pictureOfEarth.PictureOfEarthViewmodel

class EarthFragment : Fragment() {
    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfEarthViewmodel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfEarthViewmodel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.getData().observe(viewLifecycleOwner) { renderData(it) }
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun renderData(data: PictureOfEarthData) {
        when (data) {
            is PictureOfEarthData.Success -> {

                if (data.serverResponseData[0].getImageUrl().isEmpty()) {
                    toast("Link is empty")//Отобразите ошибку
                    // showError("Сообщение, что ссылка пустая")

                } else {
                    binding.earthImage.load(data.serverResponseData[0].getImageUrl()) {
                        lifecycle(this@EarthFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                        crossfade(true)
                    }
                }
            }
            is PictureOfEarthData.Loading -> {
//Отобразите загрузку
//showLoading()
            }
            is PictureOfEarthData.Error -> {
//Отобразите ошибку
//showError(data.error.message)
                toast(data.error.message)
                Log.e("///////", data.error.message.toString())
            }
        }
    }

    //    private fun setExplanation(explanation: String?) {
//        val view: ConstraintLayout? = view?.findViewById(R.id.bottom_sheet_container)
//        view?.findViewById<TextView>(R.id.bottomSheetDescription)?.text = explanation
//    }
//
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
        fun newInstance() = EarthFragment()
    }


}