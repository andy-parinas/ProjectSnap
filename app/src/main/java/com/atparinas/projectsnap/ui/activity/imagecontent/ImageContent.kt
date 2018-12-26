package com.atparinas.projectsnap.ui.activity.imagecontent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.atparinas.projectsnap.R

class ImageContent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_content)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.imagecontent_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


}
