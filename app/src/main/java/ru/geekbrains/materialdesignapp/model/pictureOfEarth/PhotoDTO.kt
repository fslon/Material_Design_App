package ru.geekbrains.materialdesignapp.model.pictureOfEarth

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

class PhotoDTO {

    private val identifier: String? = null
    private val caption: String? = null
    private val image: String? = null
    private val date: String? = null

    fun getImageUrl(): String {
        //https://api.nasa.gov/EPIC/archive/enhanced/2016/12/04/png/epic_RBG_20161204003633.png?api_key=DEMO_KEY
//        val sb = StringBuilder()
//        sb.append("https://epic.gsfc.nasa.gov/archive/enhanced/").append(getTodayDate()).append("/png/$image.png")
        return "https://epic.gsfc.nasa.gov/archive/enhanced/${getTodayDate()}/png/$image.png"
    }

    private fun getTodayDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd"))
        val dateNow = current.format(formatter)
        val date = LocalDate.parse(dateNow)
        val period = Period.of(0, 0, 5)
        val modifiedDate = date.minus(period)
        return modifiedDate.toString().replace("-", "/")
    }


}