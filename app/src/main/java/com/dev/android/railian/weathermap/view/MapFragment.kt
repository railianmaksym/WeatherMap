package com.dev.android.railian.weathermap.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dev.android.railian.weathermap.R
import com.dev.android.railian.weathermap.data_layer.pojo.WeatherInfo
import com.dev.android.railian.weathermap.di.WeatherMapApplication
import com.dev.android.railian.weathermap.di.mapFragment.MapFragmentModule
import com.dev.android.railian.weathermap.di.viewModelInjection.ViewModelFactory
import com.dev.android.railian.weathermap.util.Constants
import com.dev.android.railian.weathermap.view_model.MapFragmentViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.android.synthetic.main.bottom_sheet.*
import javax.inject.Inject


class MapFragment : Fragment(), GoogleMap.OnMapClickListener {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MapFragmentViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MapFragmentViewModel::class.java)
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WeatherMapApplication
            .appComponent()
            .mapComponentBuilder()
            .mapFragmentModule(MapFragmentModule())
            .build()
            .inject(this@MapFragment)

        bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        val mapFragment = activity?.supportFragmentManager?.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync {
            it.setOnMapClickListener(this)
        }

        viewModel.weatherInfo().observe(this, Observer {
            showWeatherBottomSheet(it)
        })
    }

    private fun showWeatherBottomSheet(weatherInfo: WeatherInfo?) {
        if (weatherInfo == null) {
            Toast.makeText(context, "Service Unreachable", Toast.LENGTH_SHORT)
                .show()
        } else {
            locationText.text = "${weatherInfo.name} ${weatherInfo.sys.countryCode}"
            generalWeatherText.text = weatherInfo.weather[0].description
            temperatureText.text = "${weatherInfo.main.temp} C"
            windText.text = "${getWindDirection(weatherInfo.wind.deg)} ${Math.round(weatherInfo.wind.speed)} m/s"
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun getWindDirection(degrees: Double): String {
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

    override fun onMapClick(coordinates: LatLng) {
        val view = activity!!.currentFocus
        if (view != null) {
            (activity?.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
                view.windowToken,
                0
            )
            viewModel.getWeatherByCoordinates(coordinates)
        }
    }

}
