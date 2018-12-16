package com.atparinas.projectsnap.ui.fragment.project.projectlist


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.ui.fragment.project.ProjectViewModel
import com.atparinas.projectsnap.ui.fragment.project.ProjectViewModelFactory
import kotlinx.android.synthetic.main.fragment_project_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext


class ProjectListFragment : Fragment(), KodeinAware, CoroutineScope {

    //Coroutines - Concurrency
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    //Dependecy Injection
    override val kodein: Kodein by closestKodein()

    //Inject ViewModelFactory
    private val projectViewModelFactory: ProjectViewModelFactory by instance()
    private lateinit var projectViewModel: ProjectViewModel

    //RecyclerView Adapter
    private val mProjectListAdapter = ProjectListAdapter()


    /*
        ################################################
                    FRAGMENTS LIFECYCLE
        ################################################
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        job = Job()
        projectViewModel = ViewModelProviders.of(this, projectViewModelFactory)
            .get(ProjectViewModel::class.java)

        Log.d("FRAGMENT_LOG", "ProjectListFragment: onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_project_list, container, false)

        //Initialize RecyclerView with Layout and Adapter
        val projectListRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_project_list)
        projectListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProjectListFragment.context)
            adapter = mProjectListAdapter
        }

        Log.d("FRAGMENT_LOG", "ProjectListFragment: onCreateView")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("FRAGMENT_LOG", "ProjectListFragment: onActivityCreated")
        subscribeToProjectList()
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        Log.d("FRAGMENT_LOG", "ProjectListFragment: onDestroy")
    }

    /*
       ################################################
                   END OF FRAGMENTS LIFECYCLE
       ################################################
    */


    private fun subscribeToProjectList() = launch {
        val projects = projectViewModel.projects.await()

        projects.observe(this@ProjectListFragment, Observer {
            if(it == null) return@Observer
            mProjectListAdapter.setProjectList(it)
        })
    }


}
