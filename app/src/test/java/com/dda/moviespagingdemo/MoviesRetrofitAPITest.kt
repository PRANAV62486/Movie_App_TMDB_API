package com.dda.moviespagingdemo

import com.dda.moviespagingdemo.models.MoviesRemoteResponse
import com.dda.moviespagingdemo.retrofit.MoviesRetrofitAPI
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Using Mockito for fake network call
class MoviesRetrofitAPITest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var moviesRetrofitAPI: MoviesRetrofitAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        moviesRetrofitAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MoviesRetrofitAPI::class.java)


    }

    //bottom edge case
    @Test
    fun getProducts_emptyList() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody(Gson().toJson(MoviesRemoteResponse(1, emptyList(), 1, 1)))
        mockWebServer.enqueue(mockResponse)

        val response = moviesRetrofitAPI.getPopularMovies(1)
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.movies?.isEmpty())
    }

    //best case
    @Test
    fun getProducts_listSize_one() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody(
            Gson().toJson(
                MoviesRemoteResponse(
                    1,
                    ArrayList<MoviesRemoteResponse.Movie>().apply {
                        this.add(MoviesRemoteResponse.Movie())
                    },
                    1,
                    1
                )
            )
        )
        mockWebServer.enqueue(mockResponse)

        val response = moviesRetrofitAPI.getPopularMovies(1)
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.movies?.size == 1)
    }

    //upper edge case
    @Test
    fun getProducts_listSize_tenThousand() = runTest {
        val mockResponse = MockResponse()

        val movies = mutableListOf<MoviesRemoteResponse.Movie>()
        for(i in 1..10000){
            movies.add(MoviesRemoteResponse.Movie(movieId = i))
        }

        mockResponse.setBody(
            Gson().toJson(
                MoviesRemoteResponse(
                    1,
                    movies,
                    1,
                    1
                )
            )
        )
        mockWebServer.enqueue(mockResponse)

        val response = moviesRetrofitAPI.getPopularMovies(1)
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.movies?.size == 10000)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}