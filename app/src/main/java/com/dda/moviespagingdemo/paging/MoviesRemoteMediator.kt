package com.dda.moviespagingdemo.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dda.moviespagingdemo.models.RemoteKeyModel
import com.dda.moviespagingdemo.models.MoviesRemoteResponse
import com.dda.moviespagingdemo.retrofit.MoviesRetrofitAPI
import com.dda.moviespagingdemo.room.MoviesDatabase
import com.dda.moviespagingdemo.utils.INITIAL_PAGE
import com.google.gson.Gson
import java.lang.Exception

@ExperimentalPagingApi
class MoviesRemoteMediator(
    private val quoteAPI: MoviesRetrofitAPI,
    private val quoteDatabase: MoviesDatabase
) : RemoteMediator<Int, MoviesRemoteResponse.Movie>() {

    val quoteDao = quoteDatabase.movieDao()
    val quoteRemoteKeysDao = quoteDatabase.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviesRemoteResponse.Movie>
    ): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    Log.d("Pranav", "Remote key refresh is ${Gson().toJson(remoteKeys)}")
                    remoteKeys?.nextPage?.minus(1) ?: INITIAL_PAGE
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    Log.d("Pranav", "Remote key prepend is ${Gson().toJson(remoteKeys)}")
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    Log.d("Pranav", "Remote key append is ${Gson().toJson(remoteKeys)}")
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = quoteAPI.getPopularMovies(currentPage)
            val endOfPaginationReached = response.totalPages == currentPage

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            quoteDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    quoteDao.clearMovies()
                    quoteRemoteKeysDao.deleteAllRemoteKeys()
                }

                quoteDao.insertMovies(response.movies ?: emptyList())
                val keys = response.movies?.map { quote ->
                    Log.d(
                        "Pranav",
                        "Added Remote key model id ${quote.movieId}, prev $prevPage, nxt $nextPage"
                    )
                    RemoteKeyModel(
                        id = quote.movieId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )

                }
                quoteRemoteKeysDao.addAllRemoteKeys(keys ?: emptyList())
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MoviesRemoteResponse.Movie>
    ): RemoteKeyModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { id ->
                quoteRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MoviesRemoteResponse.Movie>
    ): RemoteKeyModel? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                quoteRemoteKeysDao.getRemoteKeys(id = quote.movieId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MoviesRemoteResponse.Movie>
    ): RemoteKeyModel? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { quote ->
                quoteRemoteKeysDao.getRemoteKeys(id = quote.movieId)
            }
    }
}







