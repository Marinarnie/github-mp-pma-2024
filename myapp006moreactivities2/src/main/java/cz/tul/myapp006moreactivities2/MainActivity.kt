package cz.tul.myapp006moreactivities2

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

        val btnSecond = findViewById<et>(R.id.btnSecond)
        val etNickname = findViewById<EditText>(R.id.etNickname)

        btnSecond.setOnClickListener {
            val nickname = etNickname.text.toString() // získáme text z edit text pole
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("NICK_NAME", nickname)
            startActivity(intent)

        }
    }
}