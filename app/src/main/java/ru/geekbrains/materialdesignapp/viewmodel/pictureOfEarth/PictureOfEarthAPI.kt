package ru.geekbrains.materialdesignapp.viewmodel.pictureOfEarth

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.materialdesignapp.model.pictureOfEarth.POEServerResponseData

interface PictureOfEarthAPI {
    @GET("natural/images")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String):
            Call<POEServerResponseData>
}

