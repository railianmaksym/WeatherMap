package com.dev.android.railian.weathermap.view


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dev.android.railian.weathermap.data_layer.pojo.WeatherInfo
import com.dev.android.railian.weathermap.util.Constants
import com.dev.android.railian.weathermap.view_model.MapFragmentViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.dev.android.railian.weathermap.R
import com.dev.android.railian.weathermap.data_layer.pojo.Location
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), GoogleMap.OnMapClickListener {
    private val viewModel: MapFragmentViewModel by viewModel()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private var currentLocation: WeatherInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {}

            override fun onStateChanged(p0: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    if (viewModel.getFavoriteStatus(currentLocation?.cityId ?: 0)) {
                        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
                        fab.setOnClickListener {
                            fab.setOnClickListener {
                                viewModel.deleteSingleLocationFromFavorite(currentLocation!!.cityId)
                            }
                        }
                    } else {
                        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_add_to_favorite))
                        fab.setOnClickListener {
                            viewModel.addLocationToFavorites(
                                Location(
                                    currentLocation!!.cityId,
                                    currentLocation!!.name
                                )
                            )
                        }
                    }
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    fab.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
                    fab.setOnClickListener {
                        findNavController().navigate(R.id.action_mapFragment_to_favoritesFragment)
                    }
                }
            }

        })

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync {
            it.setOnMapClickListener(this)
        }

        viewModel.weatherInfo().observe(this, Observer {
            showWeatherBottomSheet(it)
        })

        viewModel.successAddLocationToFavorites().observe(this, Observer {
            Toast.makeText(context, "successAddLocationToFavorites", Toast.LENGTH_SHORT).show()
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                fab.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
        })
        viewModel.successDeleteLocation().observe(this, Observer {
            Toast.makeText(context, "successDeleteLocation", Toast.LENGTH_SHORT).show()
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                fab.setImageDrawable(resources.getDrawable(R.drawable.ic_add_to_favorite))
        })

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_favoritesFragment)
        }
    }

    private fun showWeatherBottomSheet(weatherInfo: WeatherInfo?) {
        if (weatherInfo == null) {
            Toast.makeText(context, "Service Unreachable", Toast.LENGTH_SHORT)
                .show()
        } else {
            currentLocation = weatherInfo
            locationText.text = "${weatherInfo.name} ${weatherInfo.sys.countryCode}"
            generalWeatherText.text = weatherInfo.weather[0].description
            temperatureText.text = "${Math.round(weatherInfo.main.temp)} C"
            windText.text = "${getWindDirection(weatherInfo.wind.deg)} ${Math.round(weatherInfo.wind.speed)} m/s"
            setWeatherCardState(weatherInfo.weather[0])
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindDirection(degrees: Double): String {
        return when (degrees) {
            in 340.0..20.0 -> Constants.WindDirections.NORD.displayString
            in 21.0..80.0 -> Constants.WindDirections.NORDEAST.displayString
            in 81.0..100.0 -> Constants.WindDirections.EAST.displayString
            in 101.0..170.0 -> Constants.WindDirections.SOUTHEAST.displayString
            in 171.0..190.0 -> Constants.WindDirections.SOUTH.displayString
            in 191.0..260.0 -> Constants.WindDirections.SOUTHWEST.displayString
            in 261.0..280.0 -> Constants.WindDirections.WEST.displayString
            else -> Constants.WindDirections.NORDWEST.displayString
        }
    }

    private fun setWeatherCardState(weather: WeatherInfo.Weather) {
        if (weather.icon.isEmpty()) {
            weatherImage.setImageDrawable(resources.getDrawable(R.drawable.weather_none_available))
        } else {
            when {
                weather.description.contains("clear") -> {
                    decorateWeatherCard(
                        resources.getDrawable(R.drawable.weather_clear),
                        R.color.sunnyBackground,
                        R.color.white
                    )
                }
                weather.description.contains("few") -> {
                    decorateWeatherCard(
                        resources.getDrawable(R.drawable.weather_few_clouds),
                        R.color.sunnyBackground,
                        R.color.white
                    )
                }
                !weather.description.contains("few")
                        && weather.description.contains("clouds") -> {
                    decorateWeatherCard(
                        resources.getDrawable(R.drawable.weather_clouds),
                        R.color.rainBackground,
                        R.color.white
                    )
                }
                weather.description.contains("rain")
                        && weather.description.contains("snow") -> {
                    decorateWeatherCard(
                        resources.getDrawable(R.drawable.weather_snow_rain),
                        R.color.snowBackground,
                        R.color.primary_text
                    )
                }
                weather.description.contains("rain")
                        && !weather.description.contains("snow") -> {
                    decorateWeatherCard(
                        resources.getDrawable(R.drawable.weather_rain_day),
                        R.color.rainBackground,
                        R.color.white
                    )
                }
                weather.description.contains("storm") -> {
                    decorateWeatherCard(
                        resources.getDrawable(R.drawable.weather_storm),
                        R.color.snowBackground,
                        R.color.primary_text
                    )
                }
                !weather.description.contains("rain")
                        && weather.description.contains("snow") -> {
                    decorateWeatherCard(
                        resources.getDrawable(R.drawable.weather_snow),
                        R.color.snowBackground,
                        R.color.primary_text
                    )
                }
                weather.description.contains("mist") -> {
                    decorateWeatherCard(
                        resources.getDrawable(R.drawable.weather_mist),
                        R.color.snowBackground,
                        R.color.primary_text
                    )
                }
                else -> {
                    decorateWeatherCard(
                        resources.getDrawable(R.drawable.weather_clouds),
                        R.color.rainBackground,
                        R.color.white
                    )
                }
            }
        }
    }

    private fun decorateWeatherCard(weatherDrawable: Drawable, colorBack: Int, colorFont: Int) {
        weatherImage.setImageDrawable(weatherDrawable)
        weatherCard.setBackgroundColor(resources.getColor(colorBack))
        generalWeatherText.setTextColor(resources.getColor(colorFont))
        locationText.setTextColor(resources.getColor(colorFont))
        temperatureText.setTextColor(resources.getColor(colorFont))
        windText.setTextColor(resources.getColor(colorFont))
    }

    override fun onMapClick(coordinates: LatLng) {
        viewModel.getWeatherByCoordinates(coordinates)
    }

}
