//package com.example.calendar
//
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class DetailEventActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_detail_event)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}
package com.example.calendar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailEventActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var uploadImageButton: ImageButton
    private lateinit var eventImageView: ImageView

    private val IMAGE_PICK_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        backButton = findViewById(R.id.btnBack)
        uploadImageButton = findViewById(R.id.btnUploadImage)
        eventImageView = findViewById(R.id.ivEventImage)

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
            pickImageFromGallery()
        }
    }

    // Spuštění galerie
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    // Zpracování vybraného obrázku
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data
            eventImageView.setImageURI(imageUri)
            // 📌 Později zde můžeme uložit cestu obrázku
        }
    }
}
