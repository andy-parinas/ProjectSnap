package com.atparinas.projectsnap.data.repository

import android.arch.lifecycle.LiveData
import com.atparinas.projectsnap.data.entity.Project

interface ProjectRepository {

    suspend fun insetProject(project: Project)

    suspend fun updateProject(project: Project)

    suspend fun deleteProject(project: Project)

    suspend fun getAllProjects(): LiveData<List<Project>>

    suspend fun findProject(name: String): LiveData<List<Project>>
}