package ru.geekbrains.materialdesignapp.model.pictureOfEarth

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.materialdesignapp.BuildConfig
import ru.geekbrains.materialdesignapp.viewmodel.pictureOfEarth.PictureOfEarthAPI
import java.io.IOException

class POERetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"
    fun getRetrofitImpl(): PictureOfEarthAPI {
        val poeRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            )
            .client(createOkHttpClient(POEInterceptor()))
            .build()
        return poeRetrofit.create(PictureOfEarthAPI::class.java)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        )
        return httpClient.build()
    }

    inner class POEInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", BuildConfig.NASA_API_KEY).build()
            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()

            return chain.proceed(request)
        }
    }
}






















