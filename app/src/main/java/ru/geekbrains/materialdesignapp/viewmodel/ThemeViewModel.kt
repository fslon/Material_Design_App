package ru.geekbrains.materialdesignapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import ru.geekbrains.materialdesignapp.R

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences =
        application.getSharedPreferences("ThemePreferences", Context.MODE_PRIVATE)

    fun getSelectedTheme(): Int {
        return sharedPreferences.getInt("selected_theme", R.style.Theme_MaterialDesignApp)
    }

    fun saveSelectedTheme(themeId: Int) {
        sharedPreferences.edit().putInt("selected_theme", themeId).apply()
    }
}


