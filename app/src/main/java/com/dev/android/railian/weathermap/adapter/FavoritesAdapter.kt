package com.dev.android.railian.weathermap.adapter

import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.dev.android.railian.weathermap.R
import com.dev.android.railian.weathermap.data_layer.pojo.WeatherInfo
import com.dev.android.railian.weathermap.util.Constants
import com.dev.android.railian.weathermap.view.FavoritesView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.bottom_sheet.*

class FavoritesAdapter(private var locations: List<WeatherInfo>, private val favoritesView: FavoritesView) :
    RecyclerView.Adapter<FavoritesAdapter.FavoriteLocationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteLocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FavoriteLocationViewHolder(inflater.inflate(R.layout.bottom_sheet, parent, false))
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: FavoriteLocationViewHolder, position: Int) {
        showWeatherInfo(holder, locations[position])
        holder.weather_layout.setOnLongClickListener {
            AlertDialog.Builder(it.context)
                .setTitle("Are you sure?")
                .setMessage("Do you wand delete this location from favorites?")
                .setPositiveButton("OK") { _, _ ->
                    favoritesView.deleteLocationFromFavorites(locations[position].cityId)
                }
                .setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }
                .create()
                .show()
            return@setOnLongClickListener true
        }
    }

    private fun showWeatherInfo(holder: FavoriteLocationViewHolder, weatherInfo: WeatherInfo) {
        holder.locationText.text = "${weatherInfo.name} ${weatherInfo.sys.countryCode}"
        holder.generalWeatherText.text = weatherInfo.weather[0].description
        holder.temperatureText.text = "${Math.round(weatherInfo.main.temp)} C"
        holder.windText.text = "${getWindDirection(weatherInfo.wind.deg)} ${Math.round(weatherInfo.wind.speed)} m/s"
        setWeatherCardState(holder, weatherInfo.weather[0])
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

    private fun setWeatherCardState(holder: FavoriteLocationViewHolder, weather: WeatherInfo.Weather) {
        if (weather.icon.isEmpty()) {
            holder.weatherImage.setImageDrawable(holder.weatherCard.context.resources.getDrawable(R.drawable.weather_none_available))
        } else {
            when {
                weather.description.contains("clear") -> {
                    decorateWeatherCard(
                        holder,
                        holder.weatherCard.context.resources.getDrawable(R.drawable.weather_clear),
                        R.color.sunnyBackground,
                        R.color.white
                    )
                }
                weather.description.contains("few") -> {
                    decorateWeatherCard(
                        holder,
                        holder.weatherCard.context.resources.getDrawable(R.drawable.weather_few_clouds),
                        R.color.sunnyBackground,
                        R.color.white
                    )
                }
                !weather.description.contains("few")
                        && weather.description.contains("clouds") -> {
                    decorateWeatherCard(
                        holder,
                        holder.weatherCard.context.resources.getDrawable(R.drawable.weather_clouds),
                        R.color.rainBackground,
                        R.color.white
                    )
                }
                weather.description.contains("rain")
                        && weather.description.contains("snow") -> {
                    decorateWeatherCard(
                        holder,
                        holder.weatherCard.context.resources.getDrawable(R.drawable.weather_snow_rain),
                        R.color.snowBackground,
                        R.color.primary_text
                    )
                }
                weather.description.contains("rain")
                        && !weather.description.contains("snow") -> {
                    decorateWeatherCard(
                        holder,
                        holder.weatherCard.context.resources.getDrawable(R.drawable.weather_rain_day),
                        R.color.rainBackground,
                        R.color.white
                    )
                }
                weather.description.contains("storm") -> {
                    decorateWeatherCard(
                        holder,
                        holder.weatherCard.context.resources.getDrawable(R.drawable.weather_storm),
                        R.color.snowBackground,
                        R.color.primary_text
                    )
                }
                !weather.description.contains("rain")
                        && weather.description.contains("snow") -> {
                    decorateWeatherCard(
                        holder,
                        holder.weatherCard.context.resources.getDrawable(R.drawable.weather_snow),
                        R.color.snowBackground,
                        R.color.primary_text
                    )
                }
                weather.description.contains("mist") -> {
                    decorateWeatherCard(
                        holder,
                        holder.weatherCard.context.resources.getDrawable(R.drawable.weather_mist),
                        R.color.snowBackground,
                        R.color.primary_text
                    )
                }
                else -> {
                    decorateWeatherCard(
                        holder,
                        holder.weatherCard.context.resources.getDrawable(R.drawable.weather_clouds),
                        R.color.rainBackground,
                        R.color.white
                    )
                }
            }
        }
    }

    private fun decorateWeatherCard(
        holder: FavoriteLocationViewHolder,
        weatherDrawable: Drawable,
        colorBack: Int,
        colorFont: Int
    ) {
        holder.weatherImage.setImageDrawable(weatherDrawable)
        holder.weatherCard.setBackgroundColor(holder.weatherCard.context.resources.getColor(colorBack))
        holder.generalWeatherText.setTextColor(holder.weatherCard.context.resources.getColor(colorFont))
        holder.locationText.setTextColor(holder.weatherCard.context.resources.getColor(colorFont))
        holder.temperatureText.setTextColor(holder.weatherCard.context.resources.getColor(colorFont))
        holder.windText.setTextColor(holder.weatherCard.context.resources.getColor(colorFont))
    }

    fun updateList(locations: List<WeatherInfo>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    class FavoriteLocationViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}