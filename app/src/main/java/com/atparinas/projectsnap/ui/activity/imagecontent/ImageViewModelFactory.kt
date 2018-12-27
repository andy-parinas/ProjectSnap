package com.atparinas.projectsnap.ui.activity.imagecontent

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.atparinas.projectsnap.data.repository.ImageRepository

class ImageViewModelFactory(private val imageRepository: ImageRepository):
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageViewModel(imageRepository) as T
    }
}