package com.dragonlight.secondapp.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTask(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "taskId"
    )
    val taskList: List<Task>
)
