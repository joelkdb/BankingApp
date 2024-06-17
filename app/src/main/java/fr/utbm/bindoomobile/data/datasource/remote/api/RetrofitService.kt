package fr.utbm.bindoomobile.data.datasource.remote.api

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://www.ebindoo.com/rs/ebanking/"

    val api: ClientApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(ClientApiService::class.java)
    }
}