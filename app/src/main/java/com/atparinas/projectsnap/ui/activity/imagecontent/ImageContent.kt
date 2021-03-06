package com.atparinas.projectsnap.ui.activity.imagecontent

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.data.entity.Image
import com.atparinas.projectsnap.ui.activity.task.TaskListActivity.Companion.EXTRA_PROJECT_NAME
import com.atparinas.projectsnap.ui.activity.task.TaskListActivity.Companion.EXTRA_TASK_ID
import com.atparinas.projectsnap.ui.activity.task.TaskListActivity.Companion.EXTRA_TASK_NAME
import kotlinx.android.synthetic.main.activity_image_content.*
import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class ImageContent : AppCompatActivity(), KodeinAware, CoroutineScope {

    override val kodein: Kodein by closestKodein()

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val mImageListAdapter = ImageListAdapter(this)

    private val imageViewModelFactory: ImageViewModelFactory by instance()
    private lateinit var imageViewModel: ImageViewModel

    private val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_content)
        supportActionBar?.title = intent.getStringExtra(EXTRA_TASK_NAME)
        supportActionBar?.subtitle = intent.getStringExtra(EXTRA_PROJECT_NAME)

        job = Job()

        imageViewModel = ViewModelProviders.of(this, imageViewModelFactory)
            .get(ImageViewModel::class.java)

        recycler_view_images.apply {
//            layoutManager = LinearLayoutManager(this@ImageContent)
            layoutManager = GridLayoutManager(this@ImageContent, 2)
            adapter = mImageListAdapter
        }

        button_take_picture.setOnClickListener {
            startCameraIntent()
        }

        mImageListAdapter.setImageClickListener(setImageClickListener())

        subscribeToImageList()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
            val taskId = intent.getIntExtra(EXTRA_TASK_ID, 0)
            val name = "${intent.getStringExtra(EXTRA_TASK_NAME)}_$timeStamp"
            val uri = imageViewModel.currentImagePath

            insertImage(taskId, name, uri)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.imagecontent_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){

            R.id.menu_delete_imagecontent -> deleteSelectedImage()

            R.id.menu_share_image -> sendImages()

        }

        return super.onOptionsItemSelected(item)
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

    private fun insertImage(taskId: Int, name: String, uri: String) = launch {
        imageViewModel.insertImage(taskId, name, uri)
    }

    private fun updateImageSelect(image: Image, state: Boolean) = launch{
        imageViewModel.updateImageSelect(image, state)
    }

    private fun deleteSelectedImage() = launch {
        imageViewModel.deleteSelectedImages(intent.getIntExtra(EXTRA_TASK_ID, 0))
        Toast.makeText(this@ImageContent, "Images Deleted", Toast.LENGTH_SHORT).show()

    }

    private fun sendImages() = launch {
        val selectedImages = imageViewModel.getSelectedImages(intent.getIntExtra(EXTRA_TASK_ID, 0))

        if(!selectedImages.isEmpty()){
            val intent = Intent().apply {
                action = Intent.ACTION_SEND_MULTIPLE
                type = "image/jpeg"
            }

            val files = arrayListOf<Uri>()

            selectedImages.forEach {
                val file = File(it.uri)
                val uri = FileProvider.getUriForFile(this@ImageContent,
                    "com.atparinas.projectsnap", file)
                files.add(uri)
            }

            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files)
            startActivity(Intent.createChooser(intent, "Select Share"))

            Log.d("IMAGE_CONTENT", "Uploaded")

        }

    }

    private fun getImageFile(): File{
        val imageName: String = "${intent.getStringExtra(EXTRA_PROJECT_NAME)}_${intent.getStringExtra(EXTRA_TASK_NAME)}_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(imageName, ".jpg", storageDir)

        imageViewModel.currentImagePath = imageFile.absolutePath

        return imageFile
    }

    private fun startCameraIntent() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(cameraIntent.resolveActivity(packageManager) != null){
            val imageFile = getImageFile()
            val imageUri = FileProvider.getUriForFile(this,
                "com.atparinas.projectsnap", imageFile)

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, IMAGE_REQUEST)
        }
    }

    private fun setImageClickListener(): ImageListAdapter.ImageClickListener{
        return object: ImageListAdapter.ImageClickListener {
            override fun onImageCheckUncheck(image: Image, checkBoxState: Boolean) {
                updateImageSelect(image,checkBoxState)
            }


            override fun onImageZoom(image: Image) {

            }

        }
    }

}
