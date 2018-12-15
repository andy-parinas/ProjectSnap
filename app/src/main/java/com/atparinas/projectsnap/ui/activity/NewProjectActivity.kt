package com.atparinas.projectsnap.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.atparinas.projectsnap.R

class NewProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_menu_newproject, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
