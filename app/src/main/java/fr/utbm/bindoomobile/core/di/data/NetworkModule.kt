package fr.utbm.bindoomobile.core.di.data

import fr.utbm.bindoomobile.data.datasource.remote.api.ClientApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val baseUrl = "https://test1.ebindoo.com/digicom/rs/ebanking/"

        // Provide OkHttpClient
        val okhttpClientBuilder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        okhttpClientBuilder
            .addInterceptor(logging)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

//        val client = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okhttpClientBuilder.build())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ClientApiService::class.java)
    }
}
