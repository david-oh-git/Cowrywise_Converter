package io.davidosemwota.core.network.services

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import io.davidosemwota.core.network.responses.symbols.SymbolsResponseParser
import io.davidosemwota.core.utils.getJson
import java.net.HttpURLConnection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
internal class FixerIoServiceTest {

    object MockResponses {
        const val historical_rates_status_200 = "mock-responses/get-historical-rates-status200.json"
        const val symbols_status_200 = "mock-responses/get-symbols-status200.json"
        const val status_404 = "mock-responses/get-historical-rates-status404.json"
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var service: FixerIoService
    private lateinit var mockWebServer: MockWebServer

    @BeforeEach
    fun init() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val customSymbolsConverter = GsonBuilder()
            .registerTypeAdapter(SymbolsResponseParser::class.java, SymbolsResponseParser())
            .create()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(customSymbolsConverter))
            .build()
            .create(FixerIoService::class.java)
    }

    @AfterEach
    fun reset() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestGetHistoricalRates() = runBlocking {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson(MockResponses.historical_rates_status_200))
            }
        }

        val key = "mockkey"
        val date = "2013-12-24"
        val symbols = "USD,EUR,CAD"
        val HTTP_REQ = "GET"
        val expectedRequestUrl = "/$date?access_key=$key&symbols=$symbols"

        service.getHistoricalRate(
            key = key,
            date = date,
            symbols = symbols
        )

        val request = mockWebServer.takeRequest()
        assertThat(HTTP_REQ).isEqualTo(request.method)
        assertThat(request.path).isEqualTo(expectedRequestUrl)
    }

    @Test
    fun requestGetHistoricalRates_statusCode200() = runBlocking {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson(MockResponses.historical_rates_status_200))
            }
        }

        val key = "mockkey"
        val date = "2013-12-24"
        val base = "GBP"
        val symbols = "USD,EUR,CAD"
        val expectedRequestUrl = "/$date?access_key=$key&symbols=$symbols"

        val response = service.getHistoricalRate(
            key = key,
            date = date,
            symbols = symbols
        )

        val request = mockWebServer.takeRequest()
        assertThat(response.success).isTrue()
        assertThat(request.path).isEqualTo(expectedRequestUrl)
        assertThat(response.base).isEqualTo(base)
        assertThat(response.date).isEqualTo(date)
    }

    @Test
    fun requestGetHistoricalRates_errorResponse() = runBlocking {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson(MockResponses.status_404))
            }
        }

        val key = "mockkey"
        val date = "2013-12-24"
        val symbols = "USD,EUR,CAD"
        val expectedRequestUrl = "/$date?access_key=$key&symbols=$symbols"

        val response = service.getHistoricalRate(
            key = key,
            date = date,
            symbols = symbols
        )

        val request = mockWebServer.takeRequest()
        assertThat(response.success).isFalse()
        assertThat(request.path).isEqualTo(expectedRequestUrl)
        assertThat(response.base).isNull()
        assertThat(response.date).isNull()
        assertThat(response.error?.code).isEqualTo(404)
    }
}
