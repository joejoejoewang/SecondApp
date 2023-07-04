package com.dragonlight.secondapp.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ImportantLevelWithUser(
    @Embedded
    val importantLevel: ImportantLevel,
    @Relation(
        parentColumn = "levelId",
        entityColumn = "userId"
    )
    val userList: List<User>
)
