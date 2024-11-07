package com.example.myapp012aimagetoapp

import android.R.attr.value
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapp012aimagetoapp.databinding.ActivityMainBinding
import com.yalantis.ucrop.UCrop
import java.io.File
import android.Manifest



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageView: ImageView
    private lateinit var btnTakeImage: Button
    private lateinit var btnCrop: Button
    private lateinit var part1: ImageView
    private lateinit var part2: ImageView
    private lateinit var part3: ImageView
    private lateinit var part4: ImageView

    private var currentRotationPart1 = 0f
    private var currentRotationPart2 = 0f
    private var currentRotationPart3 = 0f
    private var currentRotationPart4 = 0f

    private var imageUri: Uri? = null //Přidání proměnné pro uchování URI obrázku

    private companion object {
        private const val PICK_IMAGE = 1 // Kód pro výběr obrázku
        private const val REQUEST_CODE = 2 // Kód pro oprávnění
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageView = findViewById(R.id.ivImage)
        btnTakeImage = findViewById(R.id.btnTakeImage)
        btnCrop = findViewById(R.id.btnCrop)
        part1 = findViewById(R.id.part1)
        part2 = findViewById(R.id.part2)
        part3 = findViewById(R.id.part3)
        part4 = findViewById(R.id.part4)


        btnTakeImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }
        btnTakeImage.setOnClickListener {
            // Kontrola oprávnění
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
            } else {
                openGallery() // Otevřete galerii, pokud je oprávnění uděleno
            }

        }


    // Otevřete galerii pro výběr obrázku
//            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            startActivityForResult(intent, PICK_IMAGE)
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION), REQUEST_CODE)
//        }

        btnCrop.setOnClickListener {
            imageUri?.let { uri ->
                startCrop(uri) // Zavolejte funkci pro oříznutí obrázku
            }
        }



        // Nastavení kliknutí na každou část obrázku pro otáčení
        part1.setOnClickListener { rotateImage(part1) }
        part2.setOnClickListener { rotateImage(part2) }
        part3.setOnClickListener { rotateImage(part3) }
        part4.setOnClickListener { rotateImage(part4) }
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE)
    }

    private fun rotateImage(imageView: ImageView) {
        // Otočte obrázek o 90 stupňů
        when (imageView.id) {
            R.id.part1 -> currentRotationPart1 += 90f
            R.id.part2 -> currentRotationPart2 += 90f
            R.id.part3 -> currentRotationPart3 += 90f
            R.id.part4 -> currentRotationPart4 += 90f
        }
        imageView.rotation = when (imageView.id) {
            R.id.part1 -> currentRotationPart1
            R.id.part2 -> currentRotationPart2
            R.id.part3 -> currentRotationPart3
            R.id.part4 -> currentRotationPart4
            else -> 0f
        }
    }

    private fun startCrop(sourceUri: Uri) {
        // Nastavení cílové URI pro oříznutý obrázek
        val destinationUri = Uri.fromFile(File(cacheDir, "cropped_image.jpg"))

        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f) // Poměr stran (např. čtverec)
            .withMaxResultSize(500, 500) // Maximální velikost výsledného obrázku
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                // Načtěte obrázek a nastavte ho do ImageView
                imageView.setImageURI(selectedImageUri) // Zobrazte vybraný obrázek

                // Uložení URI pro oříznutí
                imageUri = selectedImageUri // Uložení URI pro pozdější oříznutí

                // Můžete také rozdělit obrázek na části zde, pokud chcete
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            if (resultUri != null) {
                // Zobrazte oříznutý obrázek v ImageView nebo v části obrázku podle potřeby
                imageView.setImageURI(resultUri)
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            // Zpracování chyby oříznutí
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Oprávnění bylo uděleno
            } else {
                // Oprávnění nebylo uděleno
            }
        }
    }
}

