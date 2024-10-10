package cz.tul.myapp006moreactivities2

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        val etInfo = findViewById<TextView>(R.id.etInfo)

        //načtení dat z intentu
        val nickname = intent.getStringExtra("NICK_NAME")
        etInfo.text = "Data z první aktivity. jméno: $nickname"

        val btnBack = findViewById<Button>(R.id.etBtnBack)
        btnBack.setOnClickListener { finish() }
    }
}