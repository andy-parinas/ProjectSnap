package com.atparinas.projectsnap.ui.activity.task

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.atparinas.projectsnap.data.repository.TaskRepository

class TaskViewModelFactory(private val taskRepository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskViewModel(taskRepository) as T
    }
}