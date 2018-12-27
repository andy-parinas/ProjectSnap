package com.atparinas.projectsnap.ui.activity.task

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.data.entity.Task
import com.atparinas.projectsnap.ui.activity.imagecontent.ImageContent
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

class TaskListActivity : AppCompatActivity(), KodeinAware, CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override val kodein: Kodein by closestKodein()

    companion object {
        val EXTRA_PROJECT_ID = "com.atparinas.projectsnap.PROJECT_ID"
        val EXTRA_PROJECT_NAME = "com.atparinas.projectsnap.PROJECT_NAME"
        val EXTRA_TASK_NAME = "com.atparinas.projectsnap.TASK_NAME"
        val EXTRA_TASK_ID = "com.atparinas.projectsnap.TASK_ID"
    }

    private val taskViewModelFactory: TaskViewModelFactory by instance()
    private lateinit var taskViewModel: TaskViewModel

    private val mTaskListAdapter = TaskListAdapter(this)

    private var projectId = -1
    private var projectName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        supportActionBar?.title = intent.getStringExtra(EXTRA_PROJECT_NAME)
        supportActionBar?.subtitle = "Task List"

        projectId = intent.getIntExtra(EXTRA_PROJECT_ID, -1)
        projectName = intent.getStringExtra(EXTRA_PROJECT_NAME)

        job = Job()

        taskViewModel = ViewModelProviders.of(this, taskViewModelFactory)
            .get(TaskViewModel::class.java)

        mTaskListAdapter.setTaskClickListener(setTaskClickListener())

        recycler_view_task_list.apply {
            layoutManager = LinearLayoutManager(this@TaskListActivity)
            adapter = mTaskListAdapter
        }

        image_view_add.setOnClickListener {
            val taskName = edit_text_task_name.text.toString()

            if(!taskName.trim().isEmpty() && projectId > -1){
                insertTask(projectId, taskName)
                edit_text_task_name.setText("")
            }
        }

        subscribeToTaskList()

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    private fun subscribeToTaskList() = launch {
//        val tasks = taskViewModel.getAllTasks(projectId)
//
        taskViewModel.setProjectId(projectId)
        val tasks = taskViewModel.tasks.await()

        tasks.observe(this@TaskListActivity, Observer {
            if(it == null) return@Observer
            mTaskListAdapter.submitList(it)
        })

    }

    private fun insertTask(projectId: Int, taskName: String) = launch {
        taskViewModel.insertTask(projectId, taskName)
        Toast.makeText(this@TaskListActivity, "Task Saved", Toast.LENGTH_SHORT).show()
    }

    private fun updateTaskStatus(task: Task, isComplete: Boolean, callback: (newStatus: Boolean)->Unit) = launch {
        taskViewModel.updateTaskStatus(task, isComplete)
        Log.d("TASK_ACTIVITY", "Status Updated")
        callback(isComplete)
    }


    private fun setTaskClickListener() : TaskListAdapter.TaskClickListnener {

        return object : TaskListAdapter.TaskClickListnener {
            override fun onTasStatusClick(task: Task, callback: (newStatus: Boolean) -> Unit) {
                val status = !task.isComplete
                Log.d("TASK_ACTIVITY", "The current status: ${task.isComplete} changed to $status")
                updateTaskStatus(task, status, callback )
            }

            override fun onTaskNameClick(task: Task) {
                val intent = Intent(this@TaskListActivity, ImageContent::class.java)
                intent.putExtra(EXTRA_TASK_NAME, task.name)
                intent.putExtra(EXTRA_PROJECT_NAME, projectName)
                intent.putExtra(EXTRA_TASK_ID, task.id)
                startActivity(intent)
            }

        }
    }


}
