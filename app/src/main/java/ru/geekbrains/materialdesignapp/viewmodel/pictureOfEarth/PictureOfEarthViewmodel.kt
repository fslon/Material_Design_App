package ru.geekbrains.materialdesignapp.viewmodel.pictureOfEarth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.materialdesignapp.BuildConfig
import ru.geekbrains.materialdesignapp.model.pictureOfEarth.POERetrofitImpl
import ru.geekbrains.materialdesignapp.model.pictureOfEarth.POEServerResponseData
import ru.geekbrains.materialdesignapp.model.pictureOfEarth.PictureOfEarthData

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
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object : Callback<POEServerResponseData> {
                override fun onResponse(
                    call: Call<POEServerResponseData>,
                    response: Response<POEServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
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
                    call: Call<POEServerResponseData>, t:
                    Throwable
                ) {
                    liveDataForViewToObserve.value = PictureOfEarthData.Error(t)
                }
            })
        }
    }
}