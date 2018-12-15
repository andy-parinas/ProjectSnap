package com.atparinas.projectsnap.ui.fragment.project


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.atparinas.projectsnap.R
import kotlinx.android.synthetic.main.fragment_project_list.*


class ProjectListFragment : Fragment() {

    private val mProjectListAdapter = ProjectListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        recycler_view_project_list.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = mProjectListAdapter
        }

        return inflater.inflate(R.layout.fragment_project_list, container, false)
    }


}
