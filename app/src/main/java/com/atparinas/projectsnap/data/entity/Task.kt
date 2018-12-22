package com.atparinas.projectsnap.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
data class Task (
    val projectId: Int,
    val name: String,
    val createdAt: Date,
    val isComplete: Boolean = false) {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}