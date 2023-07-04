package com.dragonlight.secondapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ImportantLevel(
    @PrimaryKey(autoGenerate = true)
    val levelId: Int,
    val levelGroup: String,
    val levelInfo: String,
    val levelDate: Int,
    val levelColor: Int
)
