package com.atparinas.projectsnap.ui.activity.imagecontent

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.ui.activity.task.TaskListActivity.Companion.EXTRA_TASK_ID
import kotlinx.android.synthetic.main.activity_image_content.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

class ImageContent : AppCompatActivity(), KodeinAware, CoroutineScope {

    override val kodein: Kodein by closestKodein()

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val mImageListAdapter = ImageListAdapter()

    private val imageViewModelFactory: ImageViewModelFactory by instance()
    private lateinit var imageViewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_content)

        job = Job()

        imageViewModel = ViewModelProviders.of(this, imageViewModelFactory)
            .get(ImageViewModel::class.java)

        recycler_view_images.apply {
            layoutManager = GridLayoutManager(this@ImageContent, 2)
            adapter = mImageListAdapter
        }

        button_take_picture.setOnClickListener {

        }

        subscribeToImageList()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.imagecontent_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    private fun subscribeToImageList() = launch {
        val taskId = intent.getIntExtra(EXTRA_TASK_ID, 0)
        imageViewModel.mTaskId = taskId

        val images = imageViewModel.images.await()
        images.observe(this@ImageContent, Observer {
            mImageListAdapter.submitList(it)
        })
    }

}
