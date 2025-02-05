package com.example.mycalendar


import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

// Adaptér pro zobrazování obrázků v RecyclerView
class ImageAdapter(
    private val imageUris: List<Uri>,
    private val onImageClick: (Uri) -> Unit // Přidání callbacku pro kliknutí na obrázek
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    //inner class ImageViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView) {
       inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
           init {
            imageView.setOnClickListener {
                onImageClick(imageUris[adapterPosition]) // Spuštění callbacku při kliknutí
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val imageView = ImageView(parent.context).apply {
//            layoutParams = RecyclerView.LayoutParams(200, 200)
//            scaleType = ImageView.ScaleType.CENTER_CROP
//        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUri = imageUris[position]

        try {
            val inputStream = holder.itemView.context.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            holder.imageView.setImageBitmap(bitmap)
            inputStream?.close()
        } catch (e: Exception) {e.printStackTrace()}
//        holder.imageView.setImageURI(imageUris[position])
    }

    override fun getItemCount(): Int = imageUris.size
}
