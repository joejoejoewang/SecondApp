package com.dragonlight.secondapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    val taskId: Int,
    val userId: Int,
    val date: String,
    val taskTitle: String,
    val importantLevelId: Int,
    val taskInfo: String
)
