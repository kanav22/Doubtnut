package com.kanav.newsapp.news.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

@RunWith(JUnit4::class)
abstract class BaseServiceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    protected lateinit var mockWebServer: MockWebServer

    @Before
    @Throws(IOException::class)
    fun setupMockServer() {
        mockWebServer = MockWebServer()
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Throws(IOException::class)
    fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, Collections.emptyMap())
    }

    @Throws(IOException::class)
    fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader
                ?.getResourceAsStream("api-response/$fileName")
        val source = inputStream?.source()?.buffer() ?: return
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }

}