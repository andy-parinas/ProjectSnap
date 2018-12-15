package com.atparinas.projectsnap.ui.fragment.project

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.data.entity.Project

class ProjectListAdapter : RecyclerView.Adapter<ProjectListAdapter.ProjectListViewHolder>(){

    private val mProjects = mutableListOf<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_list_item, parent, false)

        return ProjectListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mProjects.size
    }

    override fun onBindViewHolder(viewHolder: ProjectListViewHolder, position: Int) {

        val project = mProjects[position]

        viewHolder.projectName.text = project.name
        viewHolder.siteNumber.text = project.siteNumber
        viewHolder.siteName.text = project.siteName
        viewHolder.projectStatus.text = project.status

    }


    inner class ProjectListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val projectName = view.findViewById<TextView>(R.id.text_view_project_name)
        val siteNumber = view.findViewById<TextView>(R.id.text_view_site_number)
        val siteName = view.findViewById<TextView>(R.id.text_view_site_name)
        val projectStatus = view.findViewById<TextView>(R.id.text_view_project_status)

    }
}