package com.mobile.app.themovies.injection.modudles

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mobile.app.themovies.BuildConfig
import com.mobile.app.themovies.exception.NoNetworkException
import com.mobile.app.themovies.util.NetworkMonitor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofit(
            gson: Gson,
            okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    fun providesOKHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            @Named("InternetConnectionInterceptor") connectionInterceptor: Interceptor,
            @Named("ApiKeyInterceptor") apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(connectionInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    @Named("ApiKeyInterceptor")
    fun providesApiKeyInterceptor(): Interceptor {
        return Interceptor {
            chain -> kotlin.run {
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
        }
    }

    @Provides
    @Singleton
    @Named("InternetConnectionInterceptor")
    fun providesNetworkConnectionInterceptor(networkMonitor: NetworkMonitor): Interceptor {
        return Interceptor {
            chain -> if (networkMonitor.isConnected()) {
                chain.proceed(chain.request())
            } else {
                throw NoNetworkException()
            }
        }
    }

    @Singleton
    @Provides
    fun providesLogginInterceptor(@Named("isDebug") isDebug: Boolean): HttpLoggingInterceptor {
        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.level = if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logginInterceptor
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    @Singleton
    @Provides
    @Named("isDebug")
    fun providesIsDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Singleton
    fun providesOkttpCache(file: File): Cache = Cache(file, 1024*2014*10)

    @Provides
    @Singleton
    fun provideFile(context: Context): File = context.filesDir
}