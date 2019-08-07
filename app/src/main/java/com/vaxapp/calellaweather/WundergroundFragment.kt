package com.vaxapp.calellaweather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vaxapp.calellaweather.data.ServiceFactory
import com.vaxapp.calellaweather.data.yahoo.WundergroundService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info

class WundergroundFragment : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val log = AnkoLogger("WundergroundFragment")
    private val navigator = Navigator()

    private var description: TextView? = null
    private var latitude: TextView? = null
    private var longitude: TextView? = null
    private var temperature: TextView? = null
    private var weatherText: TextView? = null
    private var attributionText: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        description = view!!.findViewById(R.id.description) as TextView
        latitude = view!!.findViewById(R.id.latitude) as TextView
        longitude = view!!.findViewById(R.id.longitude) as TextView
        temperature = view!!.findViewById(R.id.temperature) as TextView
        weatherText = view!!.findViewById(R.id.weather_text) as TextView
        attributionText = view!!.findViewById(R.id.attribution) as TextView

        attributionText!!.text = getString(R.string.powered_by, "Weather Underground")
        attributionText!!.setOnClickListener { activity?.let { it1 -> navigator.viewUrl(it1, "https://www.wunderground.com") } }
        //TODO: add Weather underground logo
        //https://www.wunderground.com/weather/api/d/docs?d=resources/logo-usage-guide&MR=1

        val BASE_URL = "http://api.wunderground.com/api/f3f2b65b7cdfaae9/geolookup/conditions/forecast/q/zmw:00000.327.08184.json"

        compositeDisposable.add(ServiceFactory.createRetrofitService("http://api.wunderground.com/api/f3f2b65b7cdfaae9/geolookup/conditions/forecast/q/",
                WundergroundService::class.java).getWeatherInfo(BASE_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    val display_location = result.current_observation.display_location
                    description!!.text = "Weather Underground - " + display_location.city + ", " + display_location.state + ", " + display_location.country_iso3166
                    latitude!!.text = display_location.latitude
                    longitude!!.text = display_location.longitude
                    temperature!!.text = result.current_observation.temp_c.toString()
                    weatherText!!.text = result.current_observation.weather
                    log.info("got weather data $result")
                }, { error ->
                    log.error("error getting weather", error.cause)
                }))
    }

    override fun onDestroy() {
        compositeDisposable.clear();
        super.onDestroy()
    }

    companion object {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): WundergroundFragment {
            val fragment = WundergroundFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}