package com.agzz.transformerswars.di

import com.agzz.transformerswars.BuildConfig
import com.agzz.transformerswars.data.local.prefs.SharedPreferencesManager
import com.agzz.transformerswars.data.remote.ApiHelper
import com.agzz.transformerswars.data.remote.ApiHelperImpl
import com.agzz.transformerswars.data.remote.ApiService
import com.agzz.transformerswars.repository.AllSparkRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://transformers-api.firebaseapp.com/"

@Module
@InstallIn(ApplicationComponent::class)
object TransformersApiModule {

    @Singleton
    @Provides
    fun provideHttpClient(sharedPreferencesManager: SharedPreferencesManager): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = Interceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                val requestBuilder = request.newBuilder()
                    .addHeader("Authorization", sharedPreferencesManager.authToken)
            return@Interceptor chain.proceed(requestBuilder.build())
             }
        builder.addInterceptor(interceptor)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setLenient()
            .create()
       return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}