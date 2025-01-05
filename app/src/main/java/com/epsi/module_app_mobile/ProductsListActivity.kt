package com.epsi.module_app_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.epsi.module_app_mobile.ui.theme.Module_app_mobileTheme
import okhttp3.CacheControl
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ProductsListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the products_url passed from ProductsActivity
        val productsUrl = intent.getStringExtra("products_url") ?: ""

        setContent {
            Module_app_mobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductsListScreen(
                        modifier = Modifier.padding(innerPadding),
                        productsUrl = productsUrl,
                        activity = this
                    )
                }
            }
        }
    }
}

@Composable
fun ProductsListScreen(modifier: Modifier = Modifier, productsUrl: String, activity: ComponentActivity) {
    val productsState = remember { mutableStateListOf<Product>() }
    val products = arrayListOf<Product>()

    if (productsState.isEmpty() && productsUrl.isNotEmpty()) {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url(productsUrl)
            .get()
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("NETWORK_ERROR", "Failed to fetch data: ${e.message}")
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val data = response.body?.string()
                Log.d("API_RESPONSE", "Response: $data")

                if (data != null) {
                    try {
                        val jsonResponse = JSONObject(data)
                        val jsonArray = jsonResponse.optJSONArray("record")
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val product = Product(
                                    id = i.toString(),
                                    name = jsonObject.optString("name", ""),
                                    description = jsonObject.optString("description", ""),
                                    imageUrl = jsonObject.optString("picture_url", "")
                                )
                                products.add(product)
                            }
                            activity.runOnUiThread {
                                productsState.addAll(products)
                            }
                        } else {
                            Log.e("API_ERROR", "No 'record' array or it's empty in the response")
                            activity.runOnUiThread {
                                productsState.clear()
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("JSON_PARSING_ERROR", "Error parsing JSON: ${e.message}")
                    }
                } else {
                    Log.e("API_ERROR", "Response body is null")
                }
            }
        })
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Produits",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge
        )

        if (productsState.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            // Add scroll support for long lists
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productsState.size) { index ->
                    CompactProductItem(product = productsState[index]) { selectedProduct ->
                        // Navigate to ProductDetailsActivity on click
                        val intent = Intent(activity, ProductDetailsActivity::class.java)
                        intent.putExtra("product_name", selectedProduct.name)
                        intent.putExtra("product_description", selectedProduct.description)
                        intent.putExtra("product_image_url", selectedProduct.imageUrl)
                        activity.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun CompactProductItem(product: Product, onClick: (Product) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(product) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Image
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "Product Image",
            modifier = Modifier
                .size(120.dp)
                .padding(end = 8.dp)
        )

        // Product Details
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Product Name
            Text(
                text = product.name,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                maxLines = 1
            )

            // Product Description
            Text(
                text = product.description,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                maxLines = 2
            )
        }
    }
}

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String // Field for picture_url from the API
)
