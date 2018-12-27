package com.atparinas.projectsnap.data.repository

import android.arch.lifecycle.LiveData
import com.atparinas.projectsnap.data.dao.ImageDao
import com.atparinas.projectsnap.data.entity.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepositoryImpl(private val imageDao: ImageDao) : ImageRepository {
    override suspend fun insertImage(image: Image) {
        withContext(Dispatchers.IO){
            imageDao.insert(image)
        }
    }

    override suspend fun updateImage(image: Image) {
        withContext(Dispatchers.IO){
            imageDao.update(image)
        }
    }

    override suspend fun deleteImage(image: Image) {
        withContext(Dispatchers.IO){
            imageDao.delete(image)
        }
    }

    override suspend fun getAllImages(taskId: Int): LiveData<List<Image>> {
        return withContext(Dispatchers.IO){
            return@withContext imageDao.getAllImages(taskId)
        }
    }
}