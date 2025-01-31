package com.example.calendar

import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

// Adaptér pro zobrazování obrázků v RecyclerView
class ImageAdapter(
    private val imageUris: List<Uri>,
    private val onImageClick: (Uri) -> Unit // Přidání callbacku pro kliknutí na obrázek
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView) {
        init {
            imageView.setOnClickListener {
                onImageClick(imageUris[adapterPosition]) // Spuštění callbacku při kliknutí
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imageView = ImageView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(200, 200)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return ImageViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageURI(imageUris[position])
    }

    override fun getItemCount(): Int = imageUris.size
}
