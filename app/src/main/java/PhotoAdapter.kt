package com.example.pokegnomego

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class PhotoAdapter(private val photoPaths: List<String>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoPath = photoPaths[position]
        Log.d("PhotoAdapter", "Binding photo: $photoPath")
        Glide.with(holder.imageView.context)
            .load(photoPath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?,
                    target: Target<Drawable>?, isFirstResource: Boolean
                ): Boolean {
                    Log.e("Glide", "Failed to load image: $photoPath", e)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?,
                    target: Target<Drawable>?, dataSource: com.bumptech.glide.load.DataSource?, isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return photoPaths.size
    }

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }
}
