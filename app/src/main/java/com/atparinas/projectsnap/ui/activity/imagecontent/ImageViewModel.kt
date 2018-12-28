package com.atparinas.projectsnap.ui.activity.imagecontent

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.atparinas.projectsnap.data.entity.Image
import com.atparinas.projectsnap.data.repository.ImageRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.File
import java.util.*

class ImageViewModel(private val imageRespository: ImageRepository): ViewModel() {

    var mTaskId = 0
    var currentImagePath = ""


    val images by lazy {
        GlobalScope.async(Dispatchers.IO, start = CoroutineStart.LAZY) {
            imageRespository.getAllImages(mTaskId)
        }
    }

    suspend fun insertImage(taskId: Int, name: String, uri: String){
        val image = Image(taskId, name, uri, Calendar.getInstance().time)
        imageRespository.insertImage(image)
    }

    suspend fun updateImageSelect(image: Image, state: Boolean){
        image.isSelected = state
        imageRespository.updateImage(image)
    }

    suspend fun updateImageUploadStatus(images: List<Image>){
        images.forEach {
            it.isUploaded = true
            imageRespository.updateImage(it)
        }
    }

    suspend fun deleteSelectedImages(taskId: Int){
        val images = imageRespository.getSelectedImages(taskId)
        images.forEach {
            imageRespository.deleteImage(it)
            Log.d("IMAGE_CONTENT", "$it")
        }
    }

    suspend fun getSelectedImages(taskId: Int): List<Image>{
        return imageRespository.getSelectedImages(taskId)
    }
}