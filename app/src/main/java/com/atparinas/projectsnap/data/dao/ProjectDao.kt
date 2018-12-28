package com.atparinas.projectsnap.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.atparinas.projectsnap.data.entity.Project

@Dao
interface ProjectDao {

    @Insert
    fun insert(project: Project)

    @Update
    fun update(project: Project)

    @Delete
    fun delete(project: Project)

    @Query("SELECT * FROM project_table ORDER BY createdAt ASC")
    fun getAllProject(): LiveData<List<Project>>

    @Query("SELECT * FROM project_table WHERE name LIKE :projectName ORDER BY createdAt ASC")
    fun findProject(projectName: String): LiveData<List<Project>>
}