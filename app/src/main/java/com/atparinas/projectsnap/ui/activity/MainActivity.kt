package com.atparinas.projectsnap.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.ui.fragment.project.ProjectListFragment
import com.atparinas.projectsnap.ui.fragment.project.ProjectSearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Project List"

        navigation_main.setOnNavigationItemSelectedListener(bottomNavigationSelectedListener)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.menu_add_project -> {
                val intent = Intent(this, NewProjectActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private val bottomNavigationSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

        when(menuItem.itemId){

            R.id.menu_bottom_project -> {
                replaceFragment(ProjectListFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.menu_bottom_search -> {
                replaceFragment(ProjectSearchFragment())
                return@OnNavigationItemSelectedListener true
            }

        }

         false
    }


    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_layout_main, fragment)
            commit()
        }
    }
}
