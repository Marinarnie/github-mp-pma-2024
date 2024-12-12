package com.example.myapp016avanocniappka

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import androidx.appcompat.app.AlertDialog

class Kviz_activity : AppCompatActivity() {

        // Data for questions and answers
        private val questions = listOf(
            "Vánoce jsou:" to listOf(
                "dobrý důvod proč mít vánoční prázdniny",
                "vesnička na Bílém potoce v jihovýchodních Čechách",
                "svátky připomínající Ježíšovo narození"
            ),
            "Jaký je hlavní symbol Velikonoc?" to listOf(
                "Velikonoční zajíček",
                "Ozdobené vajíčko",
                "Beránek"
            ),
            "Co znamená svátek Tří králů?" to listOf(
                "Narození Ježíše",
                "Konec Vánoc",
                "Začátek jara"
            )
        )

        private val correctAnswers = listOf(
            "svátky připomínající Ježíšovo narození",
            "Ozdobené vajíčko",
            "Konec Vánoc"
        )

        private var currentQuestionIndex = 0
        private var score = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_kviz)

            val questionTextView: TextView = findViewById(R.id.questionTextView)
            val radioGroup: RadioGroup = findViewById(R.id.otazka1)
            val nextButton: Button = findViewById(R.id.btnDalsi)
            val finishButton: Button = findViewById(R.id.btnKonec)
            val btnZpet2: ImageButton = findViewById(R.id.btnZpet2)

            btnZpet2.setOnClickListener {
                finish()
            }

            // Initialize the first question
            setQuestion(questionTextView, radioGroup)

            nextButton.setOnClickListener {
                val selectedOptionId = radioGroup.checkedRadioButtonId
                if (selectedOptionId != -1) {
                    val selectedAnswer = findViewById<RadioButton>(selectedOptionId).text.toString()
                    if (selectedAnswer == correctAnswers[currentQuestionIndex]) {
                        score++
                    }
                    currentQuestionIndex++
                    if (currentQuestionIndex < questions.size) {
                        setQuestion(questionTextView, radioGroup)
                    } else {
                        Toast.makeText(this, "Další otázky nejsou. Stiskněte tlačítko Konec.", Toast.LENGTH_SHORT)
                            .show()
                        nextButton.isEnabled = false
                    }
                } else {
                    Toast.makeText(this, "Prosím, vyberte 1 odpověď.", Toast.LENGTH_SHORT).show()
                }
            }

            finishButton.setOnClickListener {
                showResults()
            }
        }

        private fun setQuestion(questionTextView: TextView, radioGroup: RadioGroup) {
            val (question, options) = questions[currentQuestionIndex]
            questionTextView.text = "${currentQuestionIndex + 1}/${questions.size} $question"
            radioGroup.removeAllViews()

            options.forEach { option ->
                val radioButton = RadioButton(this)
                radioButton.text = option
                radioGroup.addView(radioButton)
            }
            radioGroup.clearCheck()
        }

        private fun showResults() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Výsledky kvízu")
            builder.setMessage("Získali jste $score bodů z ${questions.size}.")
            builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }

    }

