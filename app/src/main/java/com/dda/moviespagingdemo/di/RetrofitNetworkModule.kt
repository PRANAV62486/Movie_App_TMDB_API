package com.dda.moviespagingdemo.di

import com.dda.moviespagingdemo.BuildConfig
import com.dda.moviespagingdemo.retrofit.MoviesRetrofitAPI
import com.dda.moviespagingdemo.utils.API_KEY
import com.dda.moviespagingdemo.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitNetworkModule {

//    @Singleton
//    @Provides
//    fun getRetrofit(): Retrofit {
//        return Retrofit.Builder().baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create()).build()
//    }
@Provides
@Singleton
fun getOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient.apply {
        retryOnConnectionFailure(true)
        readTimeout(1, TimeUnit.MINUTES)
        connectTimeout(1, TimeUnit.MINUTES)
        writeTimeout(5, TimeUnit.MINUTES)
        addInterceptor { chain ->
            val original = chain.request()
            val http = original.url
                .newBuilder().addQueryParameter("api_key", API_KEY).build()
            val newRequest = original.newBuilder().url(http).build()
            return@addInterceptor chain.proceed(newRequest)
        }
        addInterceptor(logging)
    }.build()
    return okHttpClient.build()
}

    @Provides
    @Singleton
    fun getMoshi(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient, moshi: MoshiConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshi)
            .build()
    }

    @Singleton
    @Provides
    fun getQuoteAPI(retrofit: Retrofit): MoviesRetrofitAPI{
        return retrofit.create(MoviesRetrofitAPI::class.java)
    }
}