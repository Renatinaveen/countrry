package com.example.country

import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.*

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

import java.io.IOException
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL
import java.util.Scanner

import javax.net.ssl.HttpsURLConnection

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity<onResume> : AppCompatActivity() {

    internal lateinit var Name: TextView
    internal lateinit var Capital: TextView
    internal lateinit var Population: TextView
    internal lateinit var Region: TextView
    internal lateinit var SubRegion: TextView
    internal lateinit var NumericCode: TextView
    internal lateinit var Alpha2Code: TextView
    internal lateinit var Area: TextView
    internal lateinit var FlagURL: TextView
    internal lateinit var Alpha3Code: TextView
    internal lateinit var NativeName: TextView
    internal lateinit var Demonym: TextView
    lateinit var selectCountries : Spinner

    var url = "https://restcountries.eu/rest/v2/name/africa/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getDetails()

        selectCountries = findViewById(R.id.countries)
        val adapter = ArrayAdapter.createFromResource(this, R.array.Countries, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectCountries.setAdapter(adapter)

        selectCountries.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(position == 0){
                    url = "https://restcountries.eu/rest/v2/name/africa/"
                    getDetails()
                }else if(position == 1){
                    url = "https://restcountries.eu/rest/v2/name/china/"
                    getDetails()
                }else if(position == 2){
                    url = "https://restcountries.eu/rest/v2/name/canada/"
                    getDetails()

                }else if(position == 3){
                    url = "https://restcountries.eu/rest/v2/name/india/"
                    getDetails()

                }else if(position == 4){
                    url = "https://restcountries.eu/rest/v2/name/america/"
                    getDetails()

                }else if(position == 5){
                    url = "https://restcountries.eu/rest/v2/name/sri/"
                    getDetails()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        })
        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }

    fun getDetails() {
        val a = APIClass()
        a.retrevial(url)
        Name = findViewById(R.id.name)
        Capital = findViewById(R.id.capital)
        Population = findViewById(R.id.population)
        Region = findViewById(R.id.region)
        SubRegion = findViewById(R.id.sub_region)
        Alpha2Code = findViewById(R.id.alpha)
        NumericCode = findViewById(R.id.numeric)
        Area = findViewById(R.id.area)
        FlagURL = findViewById(R.id.flagurl)
        Alpha3Code = findViewById(R.id.alpha2)
        NativeName = findViewById(R.id.nativename)
        Demonym = findViewById(R.id.demonym)

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(Api::class.java)
        val call = api.getcountries(url)
        call.enqueue(object : Callback<List<country>> {
            override fun onResponse(call: Call<List<country>>, response: Response<List<country>>) {

                val countries = response.body()

                for (c in countries!!) {

                    Name.text = c.name
                    Capital.text = c.capital
                    Population.text = c.population
                    Region.text = c.region
                    SubRegion.text = c.subregion
                    Alpha2Code.text = c.alpha2Code
                    Alpha3Code.text = c.alpha3Code
                    NumericCode.text = c.numericCode
                    Area.text = c.area
                    NativeName.text = c.nativeName
                    Demonym.text = c.demonym
                    FlagURL.text = c.flag

                    val flag_url = FlagURL.text.toString()

                    val webView = findViewById<WebView>(R.id.flag)
                    webView.loadUrl(flag_url)
                }
            }

            override fun onFailure(call: Call<List<country>>, t: Throwable) {
                Log.d("Error", t.message)
                Toast.makeText(applicationContext, t.message + "\n Failed", Toast.LENGTH_LONG).show()
            }
        })

    }
}

