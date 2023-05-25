package ru.geekbrains.materialdesignapp.model.pictureOfEarth

sealed class PictureOfEarthData {
    data class Success(val serverResponseData: POEServerResponseData) :
        PictureOfEarthData()
    data class Error(val error: Throwable) : PictureOfEarthData()
    data class Loading(val progress: Int?) : PictureOfEarthData()
}
