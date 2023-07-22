package ru.geekbrains.materialdesignapp.viewmodel.pictureOfEarth

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.geekbrains.materialdesignapp.model.pictureOfEarth.PhotoDTO

interface PictureOfEarthAPI {
    @GET("EPIC/api/enhanced/date/{date}")
    fun getPictureOfTheDay(@Path("date") date: String):
            Call<List<PhotoDTO>>
}

