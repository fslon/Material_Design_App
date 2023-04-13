package ru.geekbrains.materialdesignapp.viewmodel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.materialdesignapp.model.PODServerResponseData

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String):
            Call<PODServerResponseData>
}
