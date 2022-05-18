package com.example.albertsonsapp

import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import com.example.albertsonsapp.service.Acromine.acromineService
import com.example.albertsonsapp.service.AcromineService
import com.example.albertsonsapp.viewmodel.MainViewModel
// import io.mockk.Call
import io.mockk.every
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import retrofit2.Call
// import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import org.junit.Assert.*

/*

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.collections.List
import retrofit2.http.Query
import retrofit2.Callback
import retrofit2.Response
*/

class MainViewModelTest {

    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000

    lateinit var placeholderApi: AcromineService
    // lateinit var jsonRepository: JsonRepository

    private lateinit var subject: MainViewModel

    private lateinit var mVarEntry1: varEntry
    private lateinit var mVarEntry2: varEntry
    private lateinit var mVarEntry3: varEntry
    private lateinit var mVarEntry4: varEntry
    private lateinit var mVarEntry5: varEntry
    private lateinit var mVarEntry6: varEntry
    // private lateinit var mVarEntrys: MutableCollection<varEntry>
    private lateinit var call: Call<List<responseOneEntry>>

    private lateinit var mlfEntry: lfEntry
    private lateinit var mResponseOneEntry: responseOneEntry

    @Before
    fun init() {
        server.start(MOCK_WEBSERVER_PORT)

        placeholderApi = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(AcromineService::class.java)

       //  jsonRepository = JsonRepository(placeholderApi)
    }

    @Before
    fun setup() {

        mVarEntry1 = varEntry("heavy meromyosin",244,1971)
        mVarEntry2 = varEntry("Heavy meromyosin",12,1975)
        mVarEntry3 = varEntry("H-meromyosin",5,1975)
        mVarEntry4 = varEntry("heavy-meromyosin",4,1977)
        mVarEntry5 = varEntry("heavy  meromyosin",1,1976)
        mVarEntry6 = varEntry("H-Meromyosin",1,1976)

        mlfEntry = lfEntry("heavy meromyosin", 267, 1971, arrayOf(mVarEntry1, mVarEntry2, mVarEntry3, mVarEntry4, mVarEntry5, mVarEntry6))
        mResponseOneEntry = responseOneEntry("HMM", arrayOf(mlfEntry))

        /*val*/ // call = Call<>listOf(mResponseOneEntry)

        subject = MainViewModel()

        assertNotNull(subject.list)
        assertNotNull(subject.listData)

    }


    @Test
    fun getAcromineResponse_onResponse_Check() {

        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("jsonplaceholder_success.json").content))
        }

        placeholderApi.getResponse("HMM","heavy").enqueue()
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertComplete()
            .assertValueCount(1)
            .assertValue {
                it.size == 2
            }
            .assertNoErrors()

        //val call
        every { acromineService.getResponse(any(),any()) } returns call

        subject.getAcromineResponse()

        subject.responseStr.value?.let { assert(it.isNotEmpty()) }
        subject.errorMessage.value?.let { assert(it.isEmpty()) }
        assertNotNull(subject.list)
        assertNotNull(subject.listData)

    }

    @Test
    fun getAcromineResponse_onFailure_Check() {


        subject.getAcromineResponse()

        subject.responseStr.value?.let { assert(it.isEmpty()) }
        subject.errorMessage.value?.let { assert(it.isNotEmpty()) }
        subject.list.let { assert(it.isEmpty()) }
        subject.listData.value?.let { assert(it.isNullOrEmpty()) }
    }

}