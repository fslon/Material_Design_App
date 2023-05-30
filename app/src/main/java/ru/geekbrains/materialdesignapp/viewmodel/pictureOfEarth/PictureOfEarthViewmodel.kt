package ru.geekbrains.materialdesignapp.viewmodel.pictureOfEarth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.materialdesignapp.BuildConfig
import ru.geekbrains.materialdesignapp.model.pictureOfEarth.POERetrofitImpl
import ru.geekbrains.materialdesignapp.model.pictureOfEarth.PhotoDTO
import ru.geekbrains.materialdesignapp.model.pictureOfEarth.PictureOfEarthData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

class PictureOfEarthViewmodel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfEarthData> =
        MutableLiveData(),
    private val retrofitImpl: POERetrofitImpl = POERetrofitImpl()
) :
    ViewModel() {
    fun getData(): LiveData<PictureOfEarthData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = PictureOfEarthData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfEarthData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(getTodayDate()).enqueue(object : Callback<List<PhotoDTO>> {
                override fun onResponse(
                    call: Call<List<PhotoDTO>>,
                    response: Response<List<PhotoDTO>>
                ) {
                    if (response.isSuccessful && response.body() != null) {

//                        response.body()!!.image
                        Log.e("*********", response.body().toString())
                        val list: List<PhotoDTO> = response.body()!!
                        Log.e("222222", list[0].getImageUrl().toString())



                        liveDataForViewToObserve.value = PictureOfEarthData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PictureOfEarthData.Error(
                                    Throwable(
                                        "Unidentified error [PictureOfEarthViewmodel] "
                                    )
                                )
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfEarthData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(
                    call: Call<List<PhotoDTO>>, t:
                    Throwable
                ) {
                    liveDataForViewToObserve.value = PictureOfEarthData.Error(t)
                }
            })
        }
    }

    private fun getTodayDate(): String {

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd"))
        val dateNow = current.format(formatter)
        val date = LocalDate.parse(dateNow)
        val period = Period.of(0, 0, 5)
        val modifiedDate = date.minus(period)
        return modifiedDate.format(formatter)

//        return "${current.year}-${current.monthValue.}-${current.dayOfMonth - 5}"
//        return "${calendar.get(Calendar.YEAR)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}"
    }

}