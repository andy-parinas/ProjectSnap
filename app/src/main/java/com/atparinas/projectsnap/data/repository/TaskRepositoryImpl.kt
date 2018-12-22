package com.atparinas.projectsnap.data.repository

import android.arch.lifecycle.LiveData
import com.atparinas.projectsnap.data.dao.TaskDao
import com.atparinas.projectsnap.data.entity.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override suspend fun insertTask(task: Task) {
        withContext(Dispatchers.IO){
            taskDao.insert(task)
        }
    }

    override suspend fun updateTask(task: Task) {
        withContext(Dispatchers.IO){
            taskDao.update(task)
        }
    }

    override suspend fun deleteTask(task: Task) {
        withContext(Dispatchers.IO){
            taskDao.delete(task)
        }
    }

    override suspend fun getAllTask(projectId: Int): LiveData<List<Task>> {
        return withContext(Dispatchers.IO){
            return@withContext taskDao.getAllTask(projectId)
        }
    }
}