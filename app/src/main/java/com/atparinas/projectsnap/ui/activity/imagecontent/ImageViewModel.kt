package com.atparinas.projectsnap.ui.activity.imagecontent

import android.arch.lifecycle.ViewModel
import com.atparinas.projectsnap.data.entity.Image
import com.atparinas.projectsnap.data.repository.ImageRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
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

}