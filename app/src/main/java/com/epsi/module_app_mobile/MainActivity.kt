package com.epsi.module_app_mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.epsi.module_app_mobile.ui.theme.Module_app_mobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val students = listOf(
            Student("Rafael", "MURO", 24),
            Student("Colin", "MORLION", 19)
        )

        setContent {
            Module_app_mobileTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LazyColumn(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(students) { student ->
                            CardButton(student)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardButton(student: Student) {
    val context = LocalContext.current
    val buttonWidth = 300.dp // Largeur des boutons
    val buttonHeight = 150.dp // Hauteur des boutons
    val purpleBgColor = colorResource(id = R.color.purple_bg) // Couleur définie dans color.xml

    Button(
        onClick = {
            val intent = Intent(context, StudentInfo::class.java).apply {
                putExtra("firstName", student.firstName)
                putExtra("lastName", student.lastName)
                putExtra("age", student.age)
            }
            context.startActivity(intent)
        },
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .padding(8.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = purpleBgColor) // Couleur du bouton
    ) {
        Card(
            modifier = Modifier
                .width(buttonWidth)
                .height(buttonHeight),
            colors = CardDefaults.cardColors(containerColor = purpleBgColor) // Couleur de la carte
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Centrer le contenu verticalement
            ) {
                Text(
                    text = student.firstName,
                    color = Color.White,
                    fontWeight = FontWeight.Bold, // Prénom en gras
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = student.lastName,
                    color = Color.White,
                    fontWeight = FontWeight.Bold, // Nom en gras
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${student.age} ans",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
