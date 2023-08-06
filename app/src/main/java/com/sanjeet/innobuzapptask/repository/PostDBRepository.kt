package com.sanjeet.innobuzapptask.repository

import androidx.lifecycle.LiveData
import com.sanjeet.innobuzapp.database.AppDatabase
import com.sanjeet.innobuzapp.database.entity.PostItem
import com.sanjeet.innobuzapptask.utils.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostDBRepository @Inject constructor(


    private val appDatabase: AppDatabase) {

    suspend fun insertAllPost(postItem:List<PostItem>):List<Long>{
        return appDatabase.postDao().insert(postItem)
    }

    // NOTE - Since we are already using LIVE-DATA no need to use suspend function
    fun getAllPosts(): LiveData<List<PostItem>> = appDatabase.postDao().getAllPost()

    val allStudent: LiveData<List<PostItem>> = appDatabase.postDao().getAllPost()
}