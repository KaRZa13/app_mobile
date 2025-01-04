package com.epsi.module_app_mobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epsi.module_app_mobile.ui.theme.Module_app_mobileTheme

class StudentInfo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Récupération des données envoyées via Intent
        val firstName = intent.getStringExtra("firstName") ?: "Nom inconnu"
        val lastName = intent.getStringExtra("lastName") ?: "Prénom inconnu"
        val age = intent.getIntExtra("age", 0)
        val email = intent.getStringExtra("email") ?: "Email inconnu"
        val option = intent.getStringExtra("optional") ?: "Option inconnue"
        val picture = intent.getIntExtra("picture", R.drawable.defaultpic) // Image par défaut

        setContent {
            Module_app_mobileTheme {
                Scaffold(
                    content = { innerPadding ->
                        StudentInfoContent(
                            firstName = firstName,
                            lastName = lastName,
                            age = age,
                            email = email,
                            option = option,
                            picture = picture,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun StudentInfoContent(
    firstName: String,
    lastName: String,
    age: Int,
    email: String,
    option: String,
    picture: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current // Pour gérer le clic sur le lien

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Image de l'étudiant
        Image(
            painter = painterResource(id = picture),
            contentDescription = "Photo de $firstName $lastName",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .padding(top = 16.dp)
        )

        // Prénom, nom et âge
        Text(
            text = "$firstName $lastName, $age ans",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Email
        Text(
            text = email,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Option centrée
        Spacer(modifier = Modifier.height(16.dp)) // Espace au-dessus de l'option
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = option,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Lien URL
        Spacer(modifier = Modifier.height(16.dp)) // Espace au-dessus du lien
        Text(
            text = "https://www.epsi.fr/",
            fontSize = 16.sp,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.epsi.fr/"))
                    context.startActivity(browserIntent)
                }
        )
    }
}

