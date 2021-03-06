package com.akhbulatov.archsample.data.global.factories

import com.akhbulatov.archsample.BuildConfig
import com.akhbulatov.archsample.data.network.GitHubApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkFactory {

    val api: GitHubApi by lazy { createGitHubApi() }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
        }

    private fun createOkhttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .build()

    private fun createRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(createOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun createGitHubApi(): GitHubApi = createRetrofit().create()
}