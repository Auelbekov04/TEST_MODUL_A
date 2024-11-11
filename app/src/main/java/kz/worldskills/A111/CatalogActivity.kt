package kz.worldskills.A111

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class CatalogActivity : AppCompatActivity() {

    private lateinit var searchInput: EditText
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var productAdapter: ProductAdapter
    private var productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        searchInput = findViewById(R.id.searchInput)
        productRecyclerView = findViewById(R.id.productRecyclerView)
        bottomNavigation = findViewById(R.id.bottomNavigationView)

        // Устанавливаем адаптер и макет для RecyclerView
        productAdapter = ProductAdapter(productList) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }
        productRecyclerView.adapter = productAdapter
        productRecyclerView.layoutManager = GridLayoutManager(this, 2)

        // Загрузка данных о товарах
//        loadProducts()

        // Фильтрация списка товаров
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterProducts(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
bottomNavigation.selectedItemId=R.id.navigation_catalog
        // Обработка нижней навигации
        bottomNavigation.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.navigation_catalog -> {
//                    val intent = Intent(this, CatalogActivity::class.java)
//                    startActivity(intent)
//                    overridePendingTransition(0,0)
//                    finish()

                    true
                }
                R.id.navigation_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    finish()
                    true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    finish()

                    true
                }
                else -> false
            }
        }
    }

    private fun loadProduct() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://your-api-url.com/api/products")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    // Обработка ошибки
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonArray = JSONArray(response.body?.string())
                    productList.clear()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val product = Product(
                            name = jsonObject.getString("name"),
                            description = jsonObject.getString("description"),
                            imageUrl = jsonObject.getString("imageUrl")
                        )
                        productList.add(product)
                    }
                    runOnUiThread {
                        productAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    private fun filterProducts(query: String) {
        val filteredList = productList.filter { it.name.contains(query, ignoreCase = true) }
        productAdapter = ProductAdapter(filteredList) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }
        productRecyclerView.adapter = productAdapter
    }
}
