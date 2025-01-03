package com.epsi.module_app_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epsi.module_app_mobile.ui.theme.Module_app_mobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val peoples = listOf(
            Student("Rafael", "MURO", 24),
            Student("Tristan", "MESSAGER", 27),
            Student("Matt", "PEAU", 22),
            Student("Colin", "MORLION", 19),
            Student("Ethan", "RAMPNOUX", 43),
            Student("Rafael", "MURO", 24),
            Student("Tristan", "MESSAGER", 27),
            Student("Matt", "PEAU", 22),
            Student("Colin", "MORLION", 19),
            Student("Ethan", "RAMPNOUX", 43),
        )

        setContent {
            Module_app_mobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(peoples) {
                            CardView(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardView(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.baseline_person_24),
                contentDescription = "Profile image",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )

            Column {
                Text(
                    text = "${student.firstName} ${student.lastName}",
                    modifier = Modifier
                        .padding(top = 24.dp)
                )
                Text(
                    text = "${student.age} ans",
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            }
        }
    }
}