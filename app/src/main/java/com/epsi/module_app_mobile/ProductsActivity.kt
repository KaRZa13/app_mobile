package com.epsi.module_app_mobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import com.epsi.module_app_mobile.ui.theme.Module_app_mobileTheme
import okhttp3.CacheControl
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Module_app_mobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductsScreen(modifier = Modifier.padding(innerPadding), this)
                }
            }
        }
    }
}

@Composable
fun ProductsScreen(modifier: Modifier = Modifier, activity: ComponentActivity) {
    val categoriesState = remember { mutableStateListOf<Category>() }
    val categories = arrayListOf<Category>()

    // Récupérer les catégories depuis l'API si la liste est vide
    if (categoriesState.isEmpty()) {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
        val mRequestUrl = "https://api.jsonbin.io/v3/b/6760342bacd3cb34a8ba8657"
        val request = Request.Builder()
            .url(mRequestUrl)
            .get()
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("Error", e.toString())
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val data = response.body?.string()
                if (data != null) {
                    try {
                        val jsonResponse = JSONObject(data)
                        val jsonArray = jsonResponse.getJSONArray("record")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val category = Category(
                                categoryId = jsonObject.optString("category_id", ""),
                                title = jsonObject.optString("title", ""),
                                productsUrl = jsonObject.optString("products_url", "")
                            )
                            categories.add(category)
                        }
                        // Mise à jour des données sur le thread principal
                        activity.runOnUiThread {
                            categoriesState.addAll(categories)
                        }
                    } catch (e: Exception) {
                        Log.e("Error", "JSON Parsing Error: ${e.message}")
                    }
                }
            }
        })
    }

    // Interface utilisateur
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rayons",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge
        )

        if (categoriesState.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categoriesState.size) { index ->
                    RayonButton(category = categoriesState[index])
                }
            }
        }
    }
}

@Composable
fun RayonButton(category: Category) {
    Button(
        onClick = {
            Log.d("Rayon", "Clicked on ${category.title}")
            // Action à exécuter lorsqu'un bouton est cliqué (par exemple : naviguer)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text(
            text = category.title,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

data class Category(
    val categoryId: String,
    val title: String,
    val productsUrl: String
)
