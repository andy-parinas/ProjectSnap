package com.atparinas.projectsnap.ui.activity.task

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.data.entity.Task

class TaskListAdapter:
        ListAdapter<Task, TaskListAdapter.TaskListViewHolder >(object: DiffUtil.ItemCallback<Task>(){
            override fun areItemsTheSame(p0: Task, p1: Task): Boolean {
                return p0.id == p1.id
            }

            override fun areContentsTheSame(p0: Task, p1: Task): Boolean {
                return p0.name == p1.name && p0.isComplete == p1.isComplete
            }

        }){

    private lateinit var mTaskClickListener: TaskClickListnener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_item, parent, false)

        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TaskListViewHolder, position: Int) {
        val task = getItem(position)

        viewHolder.taskNameTextView.text = task.name
        if(task.isComplete)
            viewHolder.isCompleteImageView.setColorFilter(R.color.taskPrimary)
    }

    fun setTaskClickListener(taskClickListnener: TaskClickListnener){
        mTaskClickListener = taskClickListnener
    }

    inner class TaskListViewHolder(view: View): RecyclerView.ViewHolder(view){
        val taskNameTextView = view.findViewById<TextView>(R.id.text_view_task_name)
        val isCompleteImageView = view.findViewById<ImageView>(R.id.image_view_complete)


        init {

            taskNameTextView.setOnClickListener {
                if(::mTaskClickListener.isInitialized)
                    mTaskClickListener.onTaskNameClick(getItem(adapterPosition))
            }

            isCompleteImageView.setOnClickListener {
                if(::mTaskClickListener.isInitialized)
                    mTaskClickListener.onTasStatusClick(getItem(adapterPosition))
            }
        }


    }

    interface TaskClickListnener {
        fun onTaskNameClick(task: Task)
        fun onTasStatusClick(task: Task)
    }

}