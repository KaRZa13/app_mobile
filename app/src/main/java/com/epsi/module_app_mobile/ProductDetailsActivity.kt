package com.epsi.module_app_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.epsi.module_app_mobile.ui.theme.Module_app_mobileTheme

class ProductDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve product details from intent
        val productName = intent.getStringExtra("product_name") ?: "Unknown Product"
        val productDescription = intent.getStringExtra("product_description") ?: "No description available"
        val productImageUrl = intent.getStringExtra("product_image_url") ?: ""

        setContent {
            Module_app_mobileTheme {
                Scaffold {
                    ProductDetailsScreen(
                        productName = productName,
                        productDescription = productDescription,
                        productImageUrl = productImageUrl,
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductDetailsScreen(
    productName: String,
    productDescription: String,
    productImageUrl: String,
    modifier: Modifier = Modifier
) {
    // Enable vertical scrolling
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Add vertical scroll
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Product Name (Top)
        Text(
            text = productName,
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Product Image
        AsyncImage(
            model = productImageUrl,
            contentDescription = "Product Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        // Product Name (Below Image)
        Text(
            text = productName,
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )

        // Product Description
        Text(
            text = "Description:\n$productDescription",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
