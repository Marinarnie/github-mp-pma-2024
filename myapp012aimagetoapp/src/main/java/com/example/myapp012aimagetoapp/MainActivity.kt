package com.example.myapp012aimagetoapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp012aimagetoapp.databinding.ActivityMainBinding
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageView: ImageView
    private lateinit var btnTakeImage: Button
    private lateinit var part1: ImageView
    private lateinit var part2: ImageView
    private lateinit var part3: ImageView
    private lateinit var part4: ImageView

    private var currentRotationPart1 = 0f
    private var currentRotationPart2 = 0f
    private var currentRotationPart3 = 0f
    private var currentRotationPart4 = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageView = findViewById(R.id.ivImage)
        btnTakeImage = findViewById(R.id.btnTakeImage)
        part1 = findViewById(R.id.part1)
        part2 = findViewById(R.id.part2)
        part3 = findViewById(R.id.part3)
        part4 = findViewById(R.id.part4)

        btnTakeImage.setOnClickListener {
            // Otevřete galerii pro výběr obrázku
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }

        // Nastavení kliknutí na každou část obrázku pro otáčení
        part1.setOnClickListener { rotateImage(part1) }
        part2.setOnClickListener { rotateImage(part2) }
        part3.setOnClickListener { rotateImage(part3) }
        part4.setOnClickListener { rotateImage(part4) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                // Načtěte obrázek a nastavte ho do ImageView
                val inputStream: InputStream? = contentResolver.openInputStream(selectedImageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Zobrazte celý obrázek v ivImage
                imageView.setImageBitmap(bitmap)

                // Rozdělte obrázek na čtyři části (pro jednoduchost použijeme bitmapu přímo)
                val width = bitmap.width / 2
                val height = bitmap.height / 2

                // Vytvoření částí obrázku
                part1.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, height))
                part2.setImageBitmap(Bitmap.createBitmap(bitmap, width, 0, width, height))
                part3.setImageBitmap(Bitmap.createBitmap(bitmap, 0, height, width, height))
                part4.setImageBitmap(Bitmap.createBitmap(bitmap, width, height, width, height))
            }
        }
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

    companion object {
        private const val PICK_IMAGE = 1
    }
}

    }
}