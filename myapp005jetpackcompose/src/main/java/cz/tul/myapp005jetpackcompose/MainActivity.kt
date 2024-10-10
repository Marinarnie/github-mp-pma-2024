package cz.tul.myapp005jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.tul.myapp005jetpackcompose.ui.theme.PMATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PMATheme { }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposePerson() {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    // Přidáme Scaffold, abychom mohli přidat TopAppBar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Moje Aplikace", color = Color.White) }, // Nastaví barvu textu na bílou
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.DarkGray,  // Nastaví pozadí na černé
                    //titleContentColor = Color.White // Nastaví barvu textu na bílou
                )
            )
        }
    )
        { innerPadding ->
            // Zbytek obsahu se vykresluje uvnitř Scaffold s paddingem
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)  // padding kolem obsahu
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Textová pole pro vstupy
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Jméno") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text("Příjmení") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = age,
                    onValueChange = {
                        // Omezíme vstup na číslice a kontrolujeme, že číslo není větší než 150
                        if (it.all { char -> char.isDigit() } && it.toIntOrNull()?.let { it <= 150 } == true) {
                            age = it
                        }
                    },
                    label = { Text("Věk (hodnota menší než 151)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = place,
                    onValueChange = { place = it },
                    label = { Text("Bydliště") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Tlačítka Odeslat a Vymazat
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            resultText = "Jmenuji se $name $surname. Je mi $age let a moje bydliště je $place."
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Odeslat")
                    }

                    Button(
                        onClick = {
                            name = ""
                            surname = ""
                            age = ""
                            place = ""
                            resultText = ""
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF0000),  // Hexadecimální barva pro pozadí tlačítka
                            contentColor = Color.White  // Barva textu na tlačítku
                        )
                    ) {
                        Text("Vymazat")
                    }
                }



                // Výsledek
                if (resultText.isNotEmpty()) {
                    Text(
                        text = resultText,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PMATheme {
    }
}