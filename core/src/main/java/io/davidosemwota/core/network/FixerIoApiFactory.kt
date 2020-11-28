package io.davidosemwota.core.network

import com.google.gson.GsonBuilder
import io.davidosemwota.core.BuildConfig
import io.davidosemwota.core.network.responses.BaseResponse
import io.davidosemwota.core.network.responses.CustomResponseParser
import io.davidosemwota.core.network.services.FixerIoService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Helper to create an instance of Fixer io [Retrofit] service.
 */
object FixerIoApiFactory {

    private val BASE_URL = "http://data.fixer.io/api/"

    /**
     *  Provider method for [HttpLoggingInterceptor] for HTTP client
     *
     *  @return Instance of http interceptor
     */
    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    /**
     *  Provides instance of [OkHttpClient]
     *
     *  @return Instance of http client
     */
    private fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(interceptor)
        }
        return clientBuilder.build()
    }

    /**
     * Provider method for [Retrofit]
     *
     * @return Instance of retrofit
     */
    private fun provideRetrofitBuilder(): Retrofit {
        val customSymbolsConverter = GsonBuilder()
            .registerTypeAdapter(BaseResponse::class.java, CustomResponseParser())
            .create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(customSymbolsConverter))
            .client(provideHttpClient(provideHttpLoggingInterceptor()))
            .build()
    }

    /**
     * Creates an instance of Fixer IO [Retrofit] service.
     */
    fun provideFixerApiService(): FixerIoService =
        provideRetrofitBuilder().create(FixerIoService::class.java)
}
