package ru.geekbrains.materialdesignapp.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.geekbrains.materialdesignapp.view.planets.EarthFragment
import ru.geekbrains.materialdesignapp.view.planets.MarsFragment
import ru.geekbrains.materialdesignapp.view.planets.WeatherFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragments = arrayOf(
        EarthFragment(), MarsFragment(), WeatherFragment()
    )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH_FRAGMENT]
            1 -> fragments[MARS_FRAGMENT]
            2 -> fragments[WEATHER_FRAGMENT]
            else -> fragments[EARTH_FRAGMENT]
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            EARTH_FRAGMENT -> "Earth"
            MARS_FRAGMENT -> "Mars"
            WEATHER_FRAGMENT -> "Weather"
            else -> "Earth"
        }
    }





    companion object {
        private const val EARTH_FRAGMENT = 0
        private const val MARS_FRAGMENT = 1
        private const val WEATHER_FRAGMENT = 2
    }
}
