package com.atparinas.projectsnap.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "project_table")
data class Project(
    val name: String,
    val siteNumber: String,
    val siteName: String,
    val status: String,
    val createdAt: Date
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}