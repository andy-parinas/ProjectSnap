package com.atparinas.projectsnap.ui.activity.imagecontent

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import com.atparinas.projectsnap.R
import com.atparinas.projectsnap.data.entity.Image

class ImageListAdapter :
    ListAdapter<Image, ImageListAdapter.ImageViewHolder>(object: DiffUtil.ItemCallback<Image>(){
        override fun areItemsTheSame(p0: Image, p1: Image): Boolean {
            return p0.id == p1.id
        }

        override fun areContentsTheSame(p0: Image, p1: Image): Boolean {
           return p0.name == p1.name && p0.uri == p1.uri
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_list_item, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        val bitmap: Bitmap = BitmapFactory.decodeFile(image.uri)

        viewHolder.imageView.setImageBitmap(bitmap)
    }


    inner class ImageViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imageView = view.findViewById<ImageView>(R.id.image_view_content)
        val isSelected = view.findViewById<CheckBox>(R.id.check_box_image_select)
    }

}