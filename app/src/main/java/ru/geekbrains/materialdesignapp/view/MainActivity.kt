package ru.geekbrains.materialdesignapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.materialdesignapp.R
import ru.geekbrains.materialdesignapp.databinding.ActivityMainBinding
import ru.geekbrains.materialdesignapp.viewmodel.ThemeViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val themeViewModel = ViewModelProvider(this).get(ThemeViewModel::class.java)
        // Установка темы приложения
        setTheme(themeViewModel.getSelectedTheme())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }


    }}
