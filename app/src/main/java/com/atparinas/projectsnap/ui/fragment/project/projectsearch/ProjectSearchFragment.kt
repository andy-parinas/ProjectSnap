package com.atparinas.projectsnap.ui.fragment.project.projectsearch


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.ui.fragment.project.ProjectViewModel
import com.atparinas.projectsnap.ui.fragment.project.ProjectViewModelFactory
import com.atparinas.projectsnap.ui.fragment.project.projectlist.ProjectListAdapter
import kotlinx.android.synthetic.main.fragment_project_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext


class ProjectSearchFragment : Fragment(), KodeinAware, CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override val kodein: Kodein by closestKodein()

    private val projectViewModelFactory: ProjectViewModelFactory by instance()
    private lateinit var projectViewModel: ProjectViewModel

    private val mProjectListAdapter = ProjectListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        projectViewModel = ViewModelProviders.of(this, projectViewModelFactory)
            .get(ProjectViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {

        val view = inflater.inflate(R.layout.fragment_project_search, container, false)

//        val recylcerViewSearchResult = view.findViewById<RecyclerView>(R.id.recycler_view_search_results)
//        recylcerViewSearchResult.apply {
//            layoutManager = LinearLayoutManager(this@ProjectSearchFragment.context)
//            adapter = mProjectListAdapter
//        }
//
//        val editTextSearch = view.findViewById<EditText>(R.id.edit_text_project_search)
//
//        val buttonSearch = view.findViewById<Button>(R.id.button_search_project)
//        buttonSearch.setOnClickListener {
//            val searchString = editTextSearch.text.toString()
//            if(!searchString.trim().isEmpty()){
//                projectViewModel.searchString.value = searchString
//            }
//        }

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view_search_results.apply {
            layoutManager = LinearLayoutManager(this@ProjectSearchFragment.context)
            adapter = mProjectListAdapter
        }

        button_search_project.setOnClickListener {
            val searchString = edit_text_project_search.text.toString()
            if(!searchString.trim().isEmpty()){
                projectViewModel.searchString.value = searchString
            }
        }

        projectViewModel.searchString.observe(this, Observer {
            if(it == null) return@Observer
            if(it.trim().isEmpty()) return@Observer

            Log.d("PROJECTSEARCH", "Search String Changed $it")
            subscribeToSearchResult()
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    private fun subscribeToSearchResult() = launch{
        val results = projectViewModel.findProject()
        results.observe(this@ProjectSearchFragment, Observer {
            Log.d("PROJECTSEARCH", "Searching for Project $it")
            mProjectListAdapter.submitList(it)
        })
    }


}
