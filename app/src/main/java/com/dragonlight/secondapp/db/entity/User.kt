package com.dragonlight.secondapp.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize


@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "contactId")
    val contactId: Int,
    val userName: String,
    val userNumber: String,
    val buyOrSell: Boolean,
    val levelId: Int
)
