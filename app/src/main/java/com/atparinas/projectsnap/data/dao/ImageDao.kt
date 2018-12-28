package com.atparinas.projectsnap.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.atparinas.projectsnap.data.entity.Image

@Dao
interface ImageDao {

    @Insert
    fun insert(image: Image)

    @Update
    fun update(image: Image)

    @Delete
    fun delete(image: Image)

    @Query("SELECT * FROM image_table WHERE task_id = :taskId ORDER BY created_at DESC")
    fun getAllImages(taskId: Int): LiveData<List<Image>>

    @Query("SELECT * FROM image_table WHERE task_id = :taskId AND is_selected = 1 ORDER BY created_at DESC")
    fun getSelectedImages(taskId: Int): List<Image>
}