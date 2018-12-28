package com.atparinas.projectsnap.ui.fragment.project

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Parcelable
import android.util.Log
import com.atparinas.projectsnap.data.entity.Project
import com.atparinas.projectsnap.data.repository.ProjectRepository
import com.atparinas.projectsnap.internal.ProjectStatus
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.*

class ProjectViewModel(private val projectRepository: ProjectRepository): ViewModel() {

    val searchString = MutableLiveData<String>()

    val projects by lazy {
        GlobalScope.async(Dispatchers.IO, start = CoroutineStart.LAZY){
            projectRepository.getAllProjects()
        }
    }


    suspend fun insertProject(
        projectName: String,
        siteNumber: String,
        siteName: String,
        status: String = ProjectStatus.NEW
    ){
        val project = Project(projectName, siteNumber, siteName, status, Calendar.getInstance().time)
        projectRepository.insetProject(project)
    }

    suspend fun findProject(): LiveData<List<Project>>{
        val searchQuery = "%${searchString.value}%"
        return projectRepository.findProject(searchQuery)
    }
}