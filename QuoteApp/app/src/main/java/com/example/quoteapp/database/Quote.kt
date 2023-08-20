package com.example.quoteapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "quote_table")
data class Quote(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var text: String?,
    var author: String?,
    var isFavourite: Boolean,
) : Parcelable
