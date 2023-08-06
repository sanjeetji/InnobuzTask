package com.sanjeet.innobuzapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanjeet.innobuzapp.database.entity.PostItem

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(postItem: List<PostItem>):List<Long>

    // NOTE - Since we are already using LIVE-DATA no need to use suspend function
    @Query("SELECT * FROM PostItem")
    fun getAllPost(): LiveData<List<PostItem>>

    @Delete
    suspend fun delete(postItem: List<PostItem>)
}