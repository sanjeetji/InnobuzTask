package com.sanjeet.maxtramachinetest.network

import com.sanjeet.innobuzapp.database.entity.PostItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<PostItem>>
}