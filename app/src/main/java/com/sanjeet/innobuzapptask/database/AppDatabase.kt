package com.sanjeet.innobuzapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sanjeet.innobuzapp.database.entity.PostItem
import com.sanjeet.innobuzapp.database.dao.PostDao

@Database(entities = [PostItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
}