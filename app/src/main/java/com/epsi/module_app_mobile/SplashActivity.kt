package com.epsi.module_app_mobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epsi.module_app_mobile.ui.theme.Module_app_mobileTheme

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Module_app_mobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SplashScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },2000)

    }
}

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .background(Color(0xFF261E48))
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_splash),
            contentDescription = "Image stylisée",
            contentScale = ContentScale.Crop, // Options : Fit, Crop, FillBounds, etc.
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    Module_app_mobileTheme {
        SplashScreen()
    }
}