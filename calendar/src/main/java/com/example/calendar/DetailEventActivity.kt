package com.example.calendar

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailEventActivity : AppCompatActivity() {

    private val PICK_IMAGES_REQUEST = 1
    private lateinit var backButton: ImageButton
    private lateinit var uploadImageButton: ImageButton
    private lateinit var imageRecyclerView: RecyclerView
    private val imageUris = mutableListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        backButton = findViewById(R.id.btnBack)
        uploadImageButton = findViewById(R.id.btnUploadImage)
        imageRecyclerView = findViewById(R.id.recyclerViewImages)
        imageRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imageRecyclerView.adapter = ImageAdapter(imageUris)

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
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    if (!imageUris.contains(imageUri)) { // Ošetření duplicit
                        imageUris.add(imageUri)
                    }
                }
            } else if (data?.data != null) {
                val imageUri = data.data!!
                if (!imageUris.contains(imageUri)) { // Ošetření duplicit
                    imageUris.add(imageUri)
                }
            }
            imageRecyclerView.adapter?.notifyDataSetChanged()
        } else if (resultCode != Activity.RESULT_OK) {
            // Zde můžeš přidat logiku pro zrušení výběru
        }
    }
}
