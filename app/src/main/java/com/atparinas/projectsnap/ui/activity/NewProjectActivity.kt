package com.atparinas.projectsnap.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.ui.fragment.project.ProjectViewModel
import com.atparinas.projectsnap.ui.fragment.project.ProjectViewModelFactory
import kotlinx.android.synthetic.main.activity_new_project.*
import kotlinx.android.synthetic.main.project_list_item.*
import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

class NewProjectActivity : AppCompatActivity(), KodeinAware, CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override val kodein: Kodein by closestKodein()

    private val projectViewModelFactory: ProjectViewModelFactory by instance()
    private lateinit var projectViewModel: ProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        job = Job()
        projectViewModel = ViewModelProviders.of(this, projectViewModelFactory)
            .get(ProjectViewModel::class.java)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_menu_newproject, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){

            R.id.menu_save_project -> {
                val projectName: String = edit_text_project_name.text.toString()
                val siteNumber: String = edit_text_site_number.text.toString()
                val siteName: String = edit_text_site_name.text.toString()

                createProject(projectName, siteNumber, siteName)

            }
        }


        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun createProject(projectName: String,siteNumber: String,siteName: String) =
        launch {
            projectViewModel.insertProject(projectName, siteNumber, siteName)
            Toast.makeText(this@NewProjectActivity, "Project Saved", Toast.LENGTH_SHORT).show()
            finish()

    }

}
