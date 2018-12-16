package com.atparinas.projectsnap.ui.fragment.project

import android.arch.lifecycle.ViewModel
import android.os.Parcelable
import com.atparinas.projectsnap.data.entity.Project
import com.atparinas.projectsnap.data.repository.ProjectRepository
import com.atparinas.projectsnap.internal.ProjectStatus
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.*

class ProjectViewModel(private val projectRepository: ProjectRepository): ViewModel() {

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


}