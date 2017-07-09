package com.vaxapp.calellaweather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vaxapp.calellaweather.data.ServiceFactory
import com.vaxapp.calellaweather.data.yahoo.YahooWeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info

class YahooWeatherFragment : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val log = AnkoLogger("YahooWeatherFragment")
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

        attributionText!!.text = getString(R.string.powered_by, "Yahoo")
        attributionText!!.setOnClickListener { navigator.viewUrl(activity, "https://www.yahoo.com/?ilc=401") }
        //TODO: add yahoo logo
        //https://developer.yahoo.com/attribution/

        //TODO: find out about constants in Kotlin
        val BASE_URL = "https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where woeid in (select woeid from geo.places(1) where text='calella')&format=json"

        compositeDisposable.add(ServiceFactory.createRetrofitService("https://query.yahooapis.com/v1/public/yql/",
                YahooWeatherService::class.java).getWeatherInfo(BASE_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    description!!.text = result.query.results.channel.title
                    latitude!!.text = result.query.results.channel.item.lat
                    longitude!!.text = result.query.results.channel.item.long
                    val temperatureInFahrenheit = result.query.results.channel.item.condition.temp.toInt()
                    temperature!!.text = ((temperatureInFahrenheit - 32) * 5 / 9).toString()
                    weatherText!!.text = result.query.results.channel.item.condition.text
                    log.info("""Got weather string ${result.query.results.channel.item.condition}""")

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

        fun newInstance(sectionNumber: Int): YahooWeatherFragment {
            val fragment = YahooWeatherFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}