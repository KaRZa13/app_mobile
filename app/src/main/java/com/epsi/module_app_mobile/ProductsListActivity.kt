package com.epsi.module_app_mobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
                        val jsonArray = jsonResponse.optJSONArray("record") // Adjusted to use 'record'
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val product = Product(
                                    id = i.toString(),
                                    name = jsonObject.optString("name", ""),
                                    description = jsonObject.optString("description", ""),
                                    imageUrl = jsonObject.optString("picture_url", "") // Extract picture_url
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
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productsState.size) { index ->
                    ProductItem(product = productsState[index])
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(120.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        // Product Image
        AsyncImage(
            model = product.imageUrl, // Fetch the image from picture_url
            contentDescription = "Product Image",
            modifier = Modifier
                .size(120.dp)
                .padding(end = 8.dp)
        )

        // Product Details
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            // Product Name
            Text(
                text = product.name,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                maxLines = 1,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Product Description
            Text(
                text = product.description,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                modifier = Modifier.padding(bottom = 4.dp)
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
