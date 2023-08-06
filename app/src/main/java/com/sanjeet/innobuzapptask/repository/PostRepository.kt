package com.sanjeet.innobuzapptask.repository

import com.sanjeet.innobuzapp.database.entity.PostItem
import com.sanjeet.innobuzapptask.utils.BaseApiResponse
import com.sanjeet.innobuzapptask.utils.NetworkResult
import com.sanjeet.maxtramachinetest.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val apiService: ApiService
): BaseApiResponse() {

//    suspend fun getAllPosts()  = flow {
//        emit(NetworkResult.Loading(true))
//        val response = apiService.getPosts()
//        emit(NetworkResult.Success(response))
//    }.catch { e ->
//        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
//    }

    suspend fun getAllPosts(): Flow<NetworkResult<List<PostItem>>> {
        return flow<NetworkResult<List<PostItem>>> {
            emit(safeApiCall { apiService.getPosts() })
        }.flowOn(Dispatchers.IO)
    }
}