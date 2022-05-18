package com.example.albertsonsapp.service

import com.example.albertsonsapp.responseOneEntry
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.collections.List
import retrofit2.http.Query

interface AcromineService {
    @GET("dictionary.py")
    fun getResponse(
        @Query("sf") sf: String?,
        @Query("lf") lf: String?
    ): Call<List<responseOneEntry>>
}

object Acromine {
    private const val BASE_URL = "http://www.nactem.ac.uk/software/acromine/"

    val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val client : OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val acromineService: AcromineService by lazy {
        retrofit.create(AcromineService::class.java)
    }
}
