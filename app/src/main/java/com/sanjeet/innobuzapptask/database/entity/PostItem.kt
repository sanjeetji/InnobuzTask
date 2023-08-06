package com.sanjeet.innobuzapp.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "PostItem")
data class PostItem(
    @PrimaryKey
    val id: Int,
    val body: String,
    val title: String,
    val userId: Int
): Parcelable