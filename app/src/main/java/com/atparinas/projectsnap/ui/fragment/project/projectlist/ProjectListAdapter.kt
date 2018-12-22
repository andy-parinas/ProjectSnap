package com.atparinas.projectsnap.ui.fragment.project.projectlist

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.data.entity.Project

//class ProjectListAdapter : RecyclerView.Adapter<ProjectListAdapter.ProjectListViewHolder>(){

class ProjectListAdapter :
    ListAdapter<Project, ProjectListAdapter.ProjectListViewHolder>(object: DiffUtil.ItemCallback<Project>(){
        override fun areItemsTheSame(p0: Project, p1: Project): Boolean {
            return p0.id == p1.id
        }

        override fun areContentsTheSame(p0: Project, p1: Project): Boolean {
            return p0.name == p1.name && p0.siteNumber == p1.siteNumber && p0.siteName == p1.siteName
        }

    }){


//    private val mProjects = mutableListOf<Project>()

    private lateinit var mItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_list_item, parent, false)

        return ProjectListViewHolder(view)
    }

//    override fun getItemCount(): Int {
//        return mProjects.size
//    }

    override fun onBindViewHolder(viewHolder: ProjectListViewHolder, position: Int) {

        val project = getItem(position)

        viewHolder.projectName.text = project.name
        viewHolder.siteNumber.text = project.siteNumber
        viewHolder.siteName.text = project.siteName
        viewHolder.projectStatus.text = project.status

    }

//    fun setProjectList(projects: List<Project>){
//        mProjects.addAll(projects)
//        notifyDataSetChanged()
//    }

    fun setItemClickListener(itemClickListener: OnItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ProjectListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val projectName = view.findViewById<TextView>(R.id.text_view_project_name)
        val siteNumber = view.findViewById<TextView>(R.id.text_view_site_number)
        val siteName = view.findViewById<TextView>(R.id.text_view_site_name)
        val projectStatus = view.findViewById<TextView>(R.id.text_view_project_status)


        init {
            view.setOnClickListener {
                if(::mItemClickListener.isInitialized){
                    mItemClickListener.onItemClicked(getItem(adapterPosition))
                }
            }
        }

    }


    interface OnItemClickListener {
        fun onItemClicked(project: Project)
    }

}