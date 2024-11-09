package com.example.myapp012aimagetoapp

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp012aimagetoapp.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageView: ImageView
    private lateinit var btnTakeImage: Button
    private lateinit var btnStart: Button
    private lateinit var btnPorovnat: Button
    private lateinit var part1: ImageView
    private lateinit var part2: ImageView
    private lateinit var part3: ImageView
    private lateinit var part4: ImageView

    private var currentRotationPart1 = 0f
    private var currentRotationPart2 = 0f
    private var currentRotationPart3 = 0f
    private var currentRotationPart4 = 0f

    private var originalRotationPart1 = 0f
    private var originalRotationPart2 = 0f
    private var originalRotationPart3 = 0f
    private var originalRotationPart4 = 0f


    private var originalBitmap: Bitmap? = null // Původní bitmapa
    private var imageUri: Uri? = null // Přidání proměnné pro uchování URI obrázku

    private companion object {
        private const val PICK_IMAGE = 1 // Kód pro výběr obrázku
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageView = findViewById(R.id.ivImage)
        btnTakeImage = findViewById(R.id.btnTakeImage)
        btnStart = findViewById(R.id.btnStart)
        btnPorovnat = findViewById(R.id.btnPorovnat)
        part1 = findViewById(R.id.part1)
        part2 = findViewById(R.id.part2)
        part3 = findViewById(R.id.part3)
        part4 = findViewById(R.id.part4)

        btnTakeImage.setOnClickListener {
            openGallery() // Otevřete galerii hned po kliknutí
        }
        btnStart.setOnClickListener {
            imageUri?.let { uri ->
                imageView.alpha = 0.2f // Ztmavte původní obrázek
                displayPuzzleParts(uri) // Zobrazte části obrázku po stisknutí tlačítka Start
            } ?: Toast.makeText(this, "Nejprve vyberte obrázek.", Toast.LENGTH_SHORT).show()
        }

        btnPorovnat.setOnClickListener {
            if (compareRotations()) { // Porovnejte rotace částí
                Toast.makeText(this, "Všechny části jsou správně otočeny!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Některé části nejsou správně otočeny.", Toast.LENGTH_SHORT)
                    .show()
            }
            imageView.alpha = 1.0f // Znovu zobrazte původní obrázek
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.data // Uložení URI obrázku
            originalBitmap = MediaStore.Images.Media.getBitmap(
                this.contentResolver,
                imageUri
            ) // Načtení původního obrázku
            imageView.setImageBitmap(originalBitmap) // Zobrazit původní obrázek v hlavním ImageView
        }
    }

    private fun displayPuzzleParts(imageUri: Uri) {
        // Načtěte bitmapu z URI
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

        // Rozdělte bitmapu na čtyři části
        val width = bitmap.width / 2
        val height = bitmap.height / 2

//        // Vytvoření čtyř částí bitmapy
        val partBitmap1 = Bitmap.createBitmap(bitmap, 0, 0, width, height) // Levý horní
        val partBitmap2 = Bitmap.createBitmap(bitmap, width, 0, width, height) // Pravý horní
        val partBitmap3 = Bitmap.createBitmap(bitmap, 0, height, width, height) // Levý dolní
        val partBitmap4 = Bitmap.createBitmap(bitmap, width, height, width, height) // Pravý dolní

        // Uložení původních rotací (0 stupňů)
        originalRotationPart1 = 0f
        originalRotationPart2 = 0f
        originalRotationPart3 = 0f
        originalRotationPart4 = 0f

        // Nastavení náhodných rotací pro každou část
        randomizeRotations()

        // Zobrazit části v ImageView
        part1.setImageBitmap(partBitmap1)
        part2.setImageBitmap(partBitmap2)
        part3.setImageBitmap(partBitmap3)
        part4.setImageBitmap(partBitmap4)

        // Aplikace rotací na jednotlivé části
        part1.rotation = currentRotationPart1
        part2.rotation = currentRotationPart2
        part3.rotation = currentRotationPart3
        part4.rotation = currentRotationPart4
    }

    private fun randomizeRotations() {
        val random = Random()

        currentRotationPart1 = random.nextInt(4) * 90f // Náhodná rotace mezi 0 a 270 stupni
        currentRotationPart2 = random.nextInt(4) * 90f
        currentRotationPart3 = random.nextInt(4) * 90f
        currentRotationPart4 = random.nextInt(4) * 90f

        // Aplikace rotací na jednotlivé části
        part1.rotation = currentRotationPart1
        part2.rotation = currentRotationPart2
        part3.rotation = currentRotationPart3
        part4.rotation = currentRotationPart4
    }

    private fun rotateImage(imageView: ImageView) {
//         Otočte obrázek o 90 stupňů
        when (imageView.id) {
            R.id.part1 -> currentRotationPart1 += 90f
            R.id.part2 -> currentRotationPart2 += 90f
            R.id.part3 -> currentRotationPart3 += 90f
            R.id.part4 -> currentRotationPart4 += 90f
        }
//
        imageView.rotation = when (imageView.id) {
            R.id.part1 -> currentRotationPart1 % 360 // Otočení o 90 stupňů cyklicky
            R.id.part2 -> currentRotationPart2 % 360
            R.id.part3 -> currentRotationPart3 % 360
            R.id.part4 -> currentRotationPart4 % 360
            else -> 0f
        }
    }

    private fun showCustomToast(message: String) {
        // Inflace vlastního layoutu
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast_1, findViewById(R.id.toast_message))

        // Nastavení textu toastu
        val toastMessage = layout.findViewById<TextView>(R.id.toast_message)
        toastMessage.text = message

        // Vytvoření a zobrazení toastu
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

    private fun compareRotations(): Boolean {
        return currentRotationPart1 == originalRotationPart1 &&
                currentRotationPart2 == originalRotationPart2 &&
                currentRotationPart3 == originalRotationPart3 &&
                currentRotationPart4 == originalRotationPart4
    }
//        // Zkontrolujte, zda jsou všechny části otočeny na 0°
//        return currentRotationPart1 == 0f && currentRotationPart2 == 0f &&
//                currentRotationPart3 == 0f && currentRotationPart4 == 0f }
}



