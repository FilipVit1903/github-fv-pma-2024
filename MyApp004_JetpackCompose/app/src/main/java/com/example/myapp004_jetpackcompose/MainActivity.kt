package com.example.myapp004_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePerson()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposePerson() {


    /*
    První řádek inicializuje stav
    pro první textové pole (zde pro jméno).
    V Compose je důležité mít stav pro každý vstup,
    aby se mohly UI prvky aktualizovat,
    když se změní vstup uživatele.

    remember znamená, že hodnotu tohoto stavu
    si Compose pamatuje mezi změnami zobrazení (recompositions).

    mutableStateOf("") nastavuje počáteční hodnotu
    jako prázdný textový řetězec.
    Kdykoliv se stav name změní, Compose znovu vykreslí části,
    které závisí na této hodnotě.

    */

    // Stavy pro jednotlivé textové vstupy
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf( "") }
    var place by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("")}
    var isValid by remember { mutableStateOf(true) }


    // Přidáme Scaffold, abychom mohli přidat TopAppBar

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Moje Aplikace", color = Color.White) }, // Nastaví barvu textu na bílou
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.DarkGray,  // Nastaví pozadí na černé
                    //titleContentColor = Color.White // Nastaví barvu textu na bílou
                )
            )
        }
    ) { innerPadding ->
        // Zbytek obsahu se vykresluje uvnitř Scaffold s paddingem
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)  // padding kolem obsahu
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Textová pole pro vstupy uvnitr sloupce
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
            //Phone Number
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    // Omezíme vstup na číslice a kontrolujeme, že číslo má počet cifer 9
                    if (it.all { char -> char.isDigit() } && it.length <= 9) {
                        phone = it
                        isValid = it.length == 9
                    }
                },
                label = { Text("Telefonní číslo (bez předvolby 9 číslic)") },
                modifier = Modifier.fillMaxWidth()
            )
            //Bydliste
            OutlinedTextField(
                value = place,
                onValueChange = { place = it },
                label = { Text("Bydliště") },
                modifier = Modifier.fillMaxWidth()
            )
            //Oblibená barva
            OutlinedTextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Oblíbená barva") },
                modifier = Modifier.fillMaxWidth()
            )

            // Btns
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                //Send btn
                Button(
                    onClick = {
                        resultText = "Jmenuji se $name $surname. Je mi $age let a moje bydliště je $place. Moje telefonní" +
                                "číslo je $phone a moje oblíbená barva je $color"
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Odeslat")
                }

                //Clear btn
                Button(
                    onClick = {
                        name = ""
                        surname = ""
                        age = ""
                        place = ""
                        resultText = ""
                        phone = ""
                        color = ""
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
fun DefaultPreview() {
    ComposePerson()
}