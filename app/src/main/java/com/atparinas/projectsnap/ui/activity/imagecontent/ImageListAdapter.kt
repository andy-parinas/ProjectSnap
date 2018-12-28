package com.atparinas.projectsnap.ui.activity.imagecontent

import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.data.entity.Image
import com.bumptech.glide.Glide
import java.io.File

class ImageListAdapter(private val context: Context) :
    ListAdapter<Image, ImageListAdapter.ImageViewHolder>(object: DiffUtil.ItemCallback<Image>(){
        override fun areItemsTheSame(p0: Image, p1: Image): Boolean {
            return p0.id == p1.id
        }

        override fun areContentsTheSame(p0: Image, p1: Image): Boolean {
           return p0.name == p1.name && p0.uri == p1.uri
        }

    }) {


    private lateinit var mImageClickListener: ImageClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_list_item, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        val file = File(image.uri)

        viewHolder.checkBoxImageSelect.isChecked = image.isSelected
        if(image.isUploaded)
            viewHolder.textViewImageUploadStatus.text = "UPLOADED"

        Glide.with(context)
            .load(file)
            .into(viewHolder.imageView)
    }

    fun setImageClickListener(imageClickListener: ImageClickListener){
        mImageClickListener = imageClickListener
    }


    inner class ImageViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imageView = view.findViewById<ImageView>(R.id.image_view_content)
        val checkBoxImageSelect = view.findViewById<CheckBox>(R.id.check_box_image_select)
        val textViewImageUploadStatus = view.findViewById<TextView>(R.id.text_view_image_upload_status)

        init {
            checkBoxImageSelect.setOnClickListener {
               if(::mImageClickListener.isInitialized){
                   mImageClickListener.onImageCheckUncheck(getItem(adapterPosition), checkBoxImageSelect.isChecked)
               }
            }
        }
    }

    interface ImageClickListener {
        fun onImageCheckUncheck(image: Image, checkBoxState: Boolean)
        fun onImageZoom(image: Image)
    }

}