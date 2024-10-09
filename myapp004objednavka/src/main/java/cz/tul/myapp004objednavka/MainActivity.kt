package cz.tul.myapp004objednavka

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        private lateinit var radioGroup: RadioGroup
        private lateinit var imageView: ImageView
        private lateinit var buttonOrder: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            radioGroup = findViewById(R.id.radioGroup)
            imageView = findViewById(R.id.imageView)
            buttonOrder = findViewById(R.id.btnobjed)

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.mikbav -> imageView.setImageResource(R.drawable.obr1) // Nahraď 'image1' skutečným názvem souboru
                    R.id.mikfle -> imageView.setImageResource(R.drawable.obr2) // Nahraď 'image2'
                    R.id.mikfun -> imageView.setImageResource(R.drawable.obr3) // Nahraď 'image3'
                }
            }

            buttonOrder.setOnClickListener {
                val selectedRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                val selectedText = selectedRadioButton.text

                // Zde můžeš například zobrazit dialog s obrázkem
                imageView.setImageResource(
                    when (selectedRadioButton.id) {
                        R.id.radioButton1 -> R.drawable.image1 // Nahraď 'image1'
                        R.id.radioButton2 -> R.drawable.image2 // Nahraď 'image2'
                        R.id.radioButton3 -> R.drawable.image3 // Nahraď 'image3'
                        else -> 0
                    }
                )
            }
        }
    }

    }

}