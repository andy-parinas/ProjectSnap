package com.atparinas.projectsnap.ui.activity.task

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.atparinas.projectsnap.data.entity.Task
import com.atparinas.projectsnap.data.repository.TaskRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.*

class TaskViewModel(private val taskRepository: TaskRepository): ViewModel() {

    private var mProjectId = -1

    val tasks by lazy {
        GlobalScope.async(Dispatchers.IO, start = CoroutineStart.LAZY){
                taskRepository.getAllTask(mProjectId)
        }
    }


    fun setProjectId(projectId: Int){
        mProjectId = projectId
    }

    suspend fun getAllTasks(projectId: Int): LiveData<List<Task>>{
        return taskRepository.getAllTask(projectId)
    }

    suspend fun insertTask(projectId: Int, name: String ){
        val task = Task(projectId, name, Calendar.getInstance().time)
        taskRepository.insertTask(task)
    }

    suspend fun updateTaskStatus(task: Task, isComplete: Boolean){
        task.isComplete = isComplete
        taskRepository.updateTask(task)
    }


}