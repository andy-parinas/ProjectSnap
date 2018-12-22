package com.atparinas.projectsnap.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.atparinas.projectsnap.data.entity.Task

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task_table WHERE projectId = :projectId ORDER BY createdAt DESC")
    fun getAllTask(projectId: Int) : LiveData<List<Task>>
}