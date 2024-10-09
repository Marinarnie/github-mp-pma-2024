package cz.tul.myapp004objednavka

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import cz.tul.myapp004objednavka.databinding.ActivityMainBinding
import kotlinx.coroutines.selects.whileSelect


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.mikbav.id -> {
                    binding.obr.setImageResource(R.mipmap.obr1)
                }

                binding.mikfle.id -> {
                    binding.obr.setImageResource(R.mipmap.obr2)
                }

                binding.mikfun.id -> {
                    binding.obr.setImageResource(R.mipmap.obr3)
                }
            }
            binding.btnobjed.setOnClickListener{binding.textobjed}
        }
    }
}


