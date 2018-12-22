package com.atparinas.projectsnap.ui.activity.task

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.atparinas.projectsnap.R

class TaskListActivity : AppCompatActivity() {

    companion object {
        val EXTRA_PROJECT_ID = "com.atparinas.projectsnap.PROJECT_ID"
        val EXTRA_PROJECT_NAME = "com.atparinas.projectsnap.PROJECT_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        supportActionBar?.title = intent.getStringExtra(EXTRA_PROJECT_NAME)
        supportActionBar?.subtitle = "Task List"



    }
}
