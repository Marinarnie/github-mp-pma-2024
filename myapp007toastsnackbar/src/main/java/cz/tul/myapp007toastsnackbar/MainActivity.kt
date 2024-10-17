package cz.tul.myapp007toastsnackbar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import cz.tul.myapp007toastsnackbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShowtoast.setOnClickListener {

            val toast = Toast.makeText( this, "Nazdar - mám hlad", Toast.LENGTH_LONG)
            toast.show()
        }

        binding.btnShowsnackbar.setOnClickListener {
            Snackbar.make(binding.root, "Já jsem snackbar", Snackbar.LENGTH_LONG)
                .setDuration(7000)
                .setAction("Zavřít") {
                    Toast.makeText(this, "Zavírám snackbar", Toast.LENGTH_LONG).show()
                }
                .show()
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}