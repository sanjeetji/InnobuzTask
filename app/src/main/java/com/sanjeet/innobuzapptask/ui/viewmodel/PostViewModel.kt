package com.sanjeet.innobuzapptest.features.posts.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjeet.innobuzapp.database.entity.PostItem
import com.sanjeet.innobuzapptask.repository.PostDBRepository
import com.sanjeet.innobuzapptask.repository.PostRepository
import com.sanjeet.innobuzapptask.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val postDbRepository: PostDBRepository):ViewModel() {


    lateinit var allStudent : LiveData<List<PostItem>>

    private var _postResponse = MutableLiveData<NetworkResult<List<PostItem>>>()
    val postResponse: LiveData<NetworkResult<List<PostItem>>> = _postResponse

    private var _postSaveResponse = MutableLiveData<List<Long>>()
    val postSaveResponse: LiveData<List<Long>> = _postSaveResponse

    init {
        fetchAllPosts()
        allStudent = postDbRepository.allStudent
    }

    private fun fetchAllPosts() {
        viewModelScope.launch {
            postRepository.getAllPosts().collect {
                _postResponse.postValue(it)
            }
        }
    }

    fun savePostIntoLocalDb(postItem:List<PostItem>){
        viewModelScope.launch {
            _postSaveResponse.postValue(postDbRepository.insertAllPost(postItem))
        }
    }


}
