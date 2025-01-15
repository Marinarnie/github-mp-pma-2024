package com.example.calendar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
//import com.example.calendar.AppDatabase.Companion.INSTANCE
import kotlinx.coroutines.launch

class DetailEventActivity : AppCompatActivity() {

    private val PICK_IMAGES_REQUEST = 1
    private lateinit var backButton: ImageButton
    private lateinit var uploadImageButton: ImageButton
    private lateinit var imageRecyclerView: RecyclerView
    private lateinit var db: AppDatabase
    private lateinit var imageDao: ImageDao
    private val imageUris = mutableListOf<ImageEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        // Inicializace databáze
        db = AppDatabase.getDatabase(this)
        imageDao = db.imageDao()

        // Načtení obrázků z databáze
        lifecycleScope.launch {
            imageUris.addAll(imageDao.getAllImages())
            imageRecyclerView.adapter?.notifyDataSetChanged()
        }

        backButton = findViewById(R.id.btnBack)
        uploadImageButton = findViewById(R.id.btnUploadImage)
        imageRecyclerView = findViewById(R.id.recyclerViewImages)
        imageRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = ImageAdapter(imageUris.map{it.uri.toUri()}.toList()) { uri -> showImageDialog(uri) }
        imageRecyclerView.adapter = adapter

        // Zobrazení informací o události
        val eventName = intent.getStringExtra("eventName")
        val eventDate = intent.getStringExtra("eventDate")
        val eventCategory = intent.getStringExtra("eventCategory")

        findViewById<TextView>(R.id.tvEventDetailName).text = eventName
        findViewById<TextView>(R.id.tvEventDetailDate).text = eventDate
        findViewById<TextView>(R.id.tvEventDetailCategory).text = eventCategory

        // Návrat zpět
        backButton.setOnClickListener {
            finish()
        }

        // Nahrání obrázku
        uploadImageButton.setOnClickListener {
            checkStoragePermission()
        }
    }

    // Kontrola oprávnění
    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 100)
        } else {
            openGalleryForImages()
        }
    }

    // Otevření galerie
    private fun openGalleryForImages() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        startActivityForResult(Intent.createChooser(intent, "Vyberte obrázky"), PICK_IMAGES_REQUEST)
    }

    // Zpracování vybraných obrázků
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGES_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri.toString()
                    val imageEntity = ImageEntity(uri = imageUri)
                    lifecycleScope.launch {
                        imageDao.insert(imageEntity)
                        imageUris.add(imageEntity)
                        imageRecyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            } else if (data?.data != null) {
                val imageUri = data.data!!.toString()
                val imageEntity = ImageEntity(uri = imageUri)
                lifecycleScope.launch {
                    imageDao.insert(imageEntity)
                    imageUris.add(imageEntity)
                    imageRecyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    // Zobrazení dialogu s obrázkem a možností smazání
    private fun showImageDialog(uri: Uri) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_image_view, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.dialogImageView)
        imageView.setImageURI(uri)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Smazat") { dialog, _ ->
                deleteImage(uri)
                dialog.dismiss()
            }
            .setNegativeButton("Zavřít") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    // Smazání obrázku
    private fun deleteImage(uri: Uri) {
        val imageEntity = imageUris.find { it.uri == uri.toString() }
        if (imageEntity != null) {
            lifecycleScope.launch {
                imageDao.delete(imageEntity)
                imageUris.remove(imageEntity)
                imageRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }
}
