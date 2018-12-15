package com.atparinas.projectsnap.ui.fragment.project


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.atparinas.projectsnap.R
import kotlinx.android.synthetic.main.fragment_project_list.*


class ProjectListFragment : Fragment() {

    private val mProjectListAdapter = ProjectListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_project_list, container, false)

        val projectListRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_project_list)

        projectListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProjectListFragment.context)
            adapter = mProjectListAdapter
        }

        return view
    }


}
