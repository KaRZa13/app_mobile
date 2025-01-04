package com.epsi.module_app_mobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epsi.module_app_mobile.ui.theme.Module_app_mobileTheme

class StudentInfo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        try {
            // Récupérez les données envoyées via l'intent
            val firstName = intent.getStringExtra("firstName") ?: "Prénom inconnu"
            val lastName = intent.getStringExtra("lastName") ?: "Nom inconnu"
            val age = intent.getIntExtra("age", -1)

            if (age == -1) {
                Log.e("StudentInfo", "Age is missing!")
                finish() // Fermer l'activité si les données sont invalides
                return
            }

            setContent {
                Module_app_mobileTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        StudentDetails(
                            firstName = firstName,
                            lastName = lastName,
                            age = age,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("StudentInfo", "Failed to retrieve student data", e)
            finish() // Fermer l'activité en cas d'erreur
        }
    }
}

@Composable
fun StudentDetails(firstName: String, lastName: String, age: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Prénom : $firstName")
        Text(text = "Nom : $lastName")
        Text(text = "Âge : $age ans")
    }
}
