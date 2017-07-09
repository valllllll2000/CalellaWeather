package com.vaxapp.calellaweather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vaxapp.calellaweather.data.ServiceFactory
import com.vaxapp.calellaweather.data.openweathermap.OpenWeatherMapService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info

class OpenWeatherMapFragment : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val log = AnkoLogger("OpenWeatherMapFragment")
    private val navigator = Navigator()

    private var description: TextView? = null
    private var latitude: TextView? = null
    private var longitude: TextView? = null
    private var temperature: TextView? = null
    private var weatherText: TextView? = null
    private var attributionText: TextView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.weather_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        description = view!!.findViewById(R.id.description) as TextView
        latitude = view!!.findViewById(R.id.latitude) as TextView
        longitude = view!!.findViewById(R.id.longitude) as TextView
        temperature = view!!.findViewById(R.id.temperature) as TextView
        weatherText = view!!.findViewById(R.id.weather_text) as TextView
        attributionText = view!!.findViewById(R.id.attribution) as TextView

        attributionText!!.text = getString(R.string.powered_by, "OpenWeatherMap")
        attributionText!!.setOnClickListener { navigator.viewUrl(activity, "https://openweathermap.org") }

        //TODO: find out about constants in Kotlin
        val BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=calella,es&APPID=0ff00eeffefffef8ff3144e9d539c3ec&units=metric"

        compositeDisposable.add(ServiceFactory.createRetrofitService("http://api.openweathermap.org/data/2.5/",
                OpenWeatherMapService::class.java).getWeatherInfo(BASE_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    description!!.text = """Open Weather Map - ${result.name}"""
                    latitude!!.text = result.coord.lat.toString()
                    longitude!!.text = result.coord.lon.toString()
                    temperature!!.text = result.main.temp.toString()
                    val weatherList = result.weather
                    if (weatherList.isNotEmpty()) {
                        weatherText!!.text = weatherList[0].description
                    }
                    log.info("""Got weather string ${result}""")

                }, { error ->
                    log.error("error getting weather", error.cause)
                }))
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    companion object {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): OpenWeatherMapFragment {
            val fragment = OpenWeatherMapFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}