package com.atparinas.projectsnap.data.repository

import android.arch.lifecycle.LiveData
import com.atparinas.projectsnap.data.dao.ProjectDao
import com.atparinas.projectsnap.data.entity.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepositoryImpl(private val projectDao: ProjectDao) : ProjectRepository {

    override suspend fun insetProject(project: Project) {
        withContext(Dispatchers.IO){
            projectDao.insert(project)
        }
    }

    override suspend fun updateProject(project: Project) {
        withContext(Dispatchers.IO){
            projectDao.update(project)
        }
    }

    override suspend fun deleteProject(project: Project) {
        withContext(Dispatchers.IO){
            projectDao.delete(project)
        }
    }

    override suspend fun getAllProjects(): LiveData<List<Project>> {
        return withContext(Dispatchers.IO){
            return@withContext projectDao.getAllProject()
        }
    }
}