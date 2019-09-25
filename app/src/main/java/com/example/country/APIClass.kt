package com.example.country

import java.net.URL
import java.util.Scanner

import javax.net.ssl.HttpsURLConnection

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

class APIClass {

    lateinit var currencycode: String
        internal set
    lateinit var currencyname: String
        internal set
    lateinit var currencysymbol: String
        internal set
    lateinit var languages_1: String
        internal set
    lateinit var languages_2: String
        internal set
    lateinit var languagesName: String
        internal set
    lateinit var languagesnativename: String
        internal set
    lateinit var translationde: String
        internal set
    lateinit var translationes: String
        internal set
    lateinit var translationfa: String
        internal set
    lateinit var translationja: String
        internal set
    lateinit var translationit: String
        internal set
    lateinit var translationbr: String
        internal set
    lateinit var translationpt: String
        internal set
    lateinit var restapiurl: String
        internal set

    fun retrevial(restapiurl: String) {
        // TODO Auto-generated constructor stub
        try {
            this.restapiurl = restapiurl
            val url = URL(this.restapiurl)
            val conn = url.openConnection() as HttpsURLConnection
            conn.requestMethod = "GET"
            conn.connect()
            val responsecode = conn.responseCode
            if (responsecode != 200)
                throw RuntimeException("HttpResponseCode: $responsecode")
            else {
                val sc = Scanner(url.openStream())
                var ResponseJSONData = ""
                while (sc.hasNext()) {
                    ResponseJSONData += sc.nextLine()
                }
                sc.close()

                val parsejson = JSONParser()
                val jsonarray = parsejson.parse(ResponseJSONData) as JSONArray

                //--------------------------------------------JSON MAIN OBJECT--------------------------------------------------------
                val jsonobjectmain = jsonarray[0] as JSONObject

                //--------------------------------------------JSON CURRENCY OBJECT--------------------------------------------------------
                val jsonArrayCurrencies = jsonobjectmain["currencies"] as JSONArray?
                val jsonCurrencyObject = jsonArrayCurrencies!![0] as JSONObject
                currencyname = (jsonCurrencyObject["name"] as String?).toString()
                currencycode = (jsonCurrencyObject["code"] as String?).toString()
                currencysymbol = (jsonCurrencyObject["symbol"] as String?).toString()

                //--------------------------------------------JSON LANGUAGES OBJECT--------------------------------------------------------
                val jsonArrayLanguages = jsonobjectmain["languages"] as JSONArray?
                val jsonLanguageObject = jsonArrayLanguages!![0] as JSONObject
                for (i in jsonArrayLanguages.indices) {
                    languages_1 = (jsonLanguageObject["iso639_1"] as String?).toString()
                    languages_2 = (jsonLanguageObject["iso639_2"] as String?).toString()
                    languagesName = (jsonLanguageObject["name"] as String?).toString()
                    languagesnativename = (jsonLanguageObject["nativeName"] as String?).toString()
                }

                //--------------------------------------------JSON TRANSLATIONS OBJECT--------------------------------------------------------
                val jsonTranslationObject = jsonobjectmain["translations"] as JSONObject?
                translationde = (jsonTranslationObject!!["de"] as String?).toString()
                translationes = (jsonTranslationObject["es"] as String?).toString()
                translationfa = (jsonTranslationObject["fa"] as String?).toString()
                translationja = (jsonTranslationObject["ja"] as String?).toString()
                translationit = (jsonTranslationObject["it"] as String?).toString()
                translationbr = (jsonTranslationObject["br"] as String?).toString()
                translationpt = (jsonTranslationObject["pt"] as String?).toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun toString(): String {
        return "APIClass{" +
                "currencycode='" + currencycode + '\''.toString() +
                ", currencyname='" + currencyname + '\''.toString() +
                ", currencysymbol='" + currencysymbol + '\''.toString() +
                ", languages_1='" + languages_1 + '\''.toString() +
                ", languages_2='" + languages_2 + '\''.toString() +
                ", languagesName='" + languagesName + '\''.toString() +
                ", languagesnativename='" + languagesnativename + '\''.toString() +
                ", translationde='" + translationde + '\''.toString() +
                ", translationes='" + translationes + '\''.toString() +
                ", translationfa='" + translationfa + '\''.toString() +
                ", translationja='" + translationja + '\''.toString() +
                ", translationit='" + translationit + '\''.toString() +
                ", translationbr='" + translationbr + '\''.toString() +
                ", translationpt='" + translationpt + '\''.toString() +
                ", restapiurl='" + restapiurl + '\''.toString() +
                '}'.toString()
    }

}
