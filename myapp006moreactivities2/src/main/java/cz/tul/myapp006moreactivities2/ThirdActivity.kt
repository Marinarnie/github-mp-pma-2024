package cz.tul.myapp006moreactivities2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_third)

        val btnBack2 = findViewById<Button>(R.id.etBtnBack2)
        btnBack2.setOnClickListener { finish() //ukončit a vrátit se zpět na předchozí obrazovku
        }
    }
}