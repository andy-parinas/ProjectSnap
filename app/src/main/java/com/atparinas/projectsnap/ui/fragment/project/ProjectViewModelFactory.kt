package com.atparinas.projectsnap.ui.fragment.project

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.atparinas.projectsnap.data.repository.ProjectRepository

class ProjectViewModelFactory(private val projectRepository: ProjectRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectViewModel(projectRepository)as T
    }
}