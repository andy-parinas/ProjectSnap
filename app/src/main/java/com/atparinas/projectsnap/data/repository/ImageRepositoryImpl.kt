package com.atparinas.projectsnap.data.repository

import android.arch.lifecycle.LiveData
import com.atparinas.projectsnap.data.dao.ImageDao
import com.atparinas.projectsnap.data.entity.Image

class ImageRepositoryImpl(private val imageDao: ImageDao) : ImageRepository {
    override suspend fun insertImage(image: Image) {
        imageDao.insert(image)
    }

    override suspend fun updateImage(image: Image) {
        imageDao.update(image)
    }

    override suspend fun deleteImage(image: Image) {
        imageDao.delete(image)
    }

    override suspend fun getAllImages(taskId: Int): LiveData<List<Image>> {
        return imageDao.getAllImages(taskId)
    }
}