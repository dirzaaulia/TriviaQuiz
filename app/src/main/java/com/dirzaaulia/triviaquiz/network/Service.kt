package com.dirzaaulia.triviaquiz.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dirzaaulia.triviaquiz.data.response.CategoryResponse
import com.dirzaaulia.triviaquiz.data.response.QuestionsResponse
import com.dirzaaulia.triviaquiz.utils.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

  @GET("api.php")
  suspend fun getQuestions(
    @Query("amount") amount: Int,
    @Query("category") categoryId: Int,
    @Query("difficulty") difficulty: String,
    @Query("type") type: String
  ): Response<QuestionsResponse>

  @GET("api_category.php")
  suspend fun getCategory(): Response<CategoryResponse>

  companion object {
    fun create(context: Context): Service {
      val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
      val chucker = ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(false)
        .build()

      val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor(chucker)
        .build()

      val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(Service::class.java)
    }
  }
}