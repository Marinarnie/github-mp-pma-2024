package com.example.mycalendar

import android.annotation.SuppressLint
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailEventActivity : AppCompatActivity() {

    private val PICK_IMAGES_REQUEST = 1
    private lateinit var backButton: ImageButton
    private lateinit var uploadImageButton: ImageButton
    private lateinit var imageRecyclerView: RecyclerView
    private val imageUris = mutableListOf<ImageEntity>()
    private lateinit var database: AppDatabase
    private var eventId: Long = -1 // ID události

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        // Inicializace databáze
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "event_database"
        ).build()

        imageRecyclerView = findViewById(R.id.recyclerViewImages)
        imageRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        backButton = findViewById(R.id.btnBack)
        uploadImageButton = findViewById(R.id.btnUploadImage)

        // Zobrazení informací o události
        val eventName = intent.getStringExtra("eventName")
        val eventDate = intent.getStringExtra("eventDate")
        val eventCategory = intent.getStringExtra("eventCategory")
//        eventId = intent.getLongExtra("eventId", -1) // Načtení ID události
        eventId = intent.getStringExtra("eventId")!!.toLong() // Načtení ID události

        findViewById<TextView>(R.id.tvEventDetailName).text = eventName
        findViewById<TextView>(R.id.tvEventDetailDate).text = eventDate
        findViewById<TextView>(R.id.tvEventDetailCategory).text = eventCategory

        // Načtení obrázků z databáze
        lifecycleScope.launch {
            loadImagesFromDatabase(eventId) // Předání ID události
        }

        // Návrat zpět
        backButton.setOnClickListener {
            finish()
        }

        // Nahrání obrázku
        uploadImageButton.setOnClickListener {
            checkStoragePermission()
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            loadImagesFromDatabase(eventId) // Předání ID události
        }
    }

    // Kontrola oprávnění
    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                100
            )
        } else {
            openGalleryForImages()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGalleryForImages()
            }
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
    @SuppressLint("WrongConstant")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK) {
            val newImages = mutableListOf<Uri>()

            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    val takeFlags =
                        data.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    contentResolver.takePersistableUriPermission(imageUri, takeFlags)
                    newImages.add(imageUri)
                }
            } else if (data?.data != null) {
                val imageUri = data.data!!
                val takeFlags =
                    data.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                contentResolver.takePersistableUriPermission(imageUri, takeFlags)
                newImages.add(imageUri)
            }

            // Přidání obrázků do seznamu
            imageUris.addAll(newImages.map { ImageEntity(uri = it.toString(), eventId = eventId) })

            // Uložení obrázků do databáze
            lifecycleScope.launch {
                saveImagesToDatabase(newImages)
                loadImagesFromDatabase(eventId) // Předání ID události
            }

            // Aktualizace RecyclerView
            imageRecyclerView.adapter = ImageAdapter(imageUris.map { it.uri.toUri() }) { uri ->
                showImageDialog(uri)
            }
        }
    }

    // Načtení obrázků z databáze
    @SuppressLint("NotifyDataSetChanged")
    private suspend fun loadImagesFromDatabase(eventId: Long) {
        val imagesFromDb =
            database.imageDao().getImagesByEventId(eventId) // Načtení obrázků podle ID události
        imageUris.clear()
        imageUris.addAll(imagesFromDb)
        imageRecyclerView.adapter = ImageAdapter(imageUris.map { it.uri.toUri() }) { uri ->
            showImageDialog(uri)
        }
    }

    // Uložení obrázků do databáze
    private suspend fun saveImagesToDatabase(newImages: List<Uri>) {
        for (uri in newImages) {
            database.imageDao().insert(
                ImageEntity(
                    uri = uri.toString(),
                    eventId = eventId
                )
            ) // Uložení s ID události
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
    @SuppressLint("NotifyDataSetChanged")
    private fun deleteImage(uri: Uri) {
        val imageEntity = imageUris.find { it.uri == uri.toString() }
        if (imageEntity != null) {
            lifecycleScope.launch {
                database.imageDao().deleteImage(imageEntity.id)
                imageUris.remove(imageEntity)
                imageRecyclerView.adapter = ImageAdapter(imageUris.map { it.uri.toUri() }) { uri ->
                    showImageDialog(uri)
                }
            }
        }
    }
}
