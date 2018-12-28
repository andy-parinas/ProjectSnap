package com.atparinas.projectsnap.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "image_table")
data class Image(
    @ColumnInfo(name="task_id") val taskId: Int,
    @ColumnInfo(name="name") var name: String,
    @ColumnInfo(name="uri") var uri: String,
    @ColumnInfo(name="created_at") var createdAt: Date,
    @ColumnInfo(name="is_selected") var isSelected: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}