package com.atparinas.projectsnap.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
data class Task (
    @ColumnInfo(name="project_id") val projectId: Int,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="created_at") val createdAt: Date,
    @ColumnInfo(name="is_complete") val isComplete: Boolean = false) {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}