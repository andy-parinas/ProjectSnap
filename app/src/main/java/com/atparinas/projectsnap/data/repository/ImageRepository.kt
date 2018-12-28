package com.atparinas.projectsnap.data.repository

import android.arch.lifecycle.LiveData
import com.atparinas.projectsnap.data.entity.Image

interface ImageRepository {

    suspend fun insertImage(image: Image)

    suspend fun updateImage(image: Image)

    suspend fun deleteImage(image: Image)

    suspend fun getAllImages(taskId: Int): LiveData<List<Image>>

    suspend fun getSelectedImages(taskId: Int): List<Image>

}