package kz.worldskills.A111

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : AppCompatActivity() {

    private lateinit var cartItems: MutableList<CartItem>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalPriceText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.selectedItemId=R.id.navigation_cart
        // Инициализация начального фрагмента


        // Слушатель выбора элементов навигации
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_catalog -> {
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    finish()

                    true
                }
                R.id.navigation_cart -> {

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
            }}
        // Инициализируем список товаров
        cartItems = mutableListOf(
            CartItem(R.drawable.image, "Товар 1", 500.0, 1),
            CartItem(R.drawable.image, "Товар 2", 1500.0, 1)
        )

        // Инициализация RecyclerView и адаптера
        val cartRecyclerView: RecyclerView = findViewById(R.id.cartRecyclerView)
        cartAdapter = CartAdapter(cartItems) {
            updateTotalPrice()
        }
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.adapter = cartAdapter

        // Общая сумма
        totalPriceText = findViewById(R.id.totalPriceText)
        updateTotalPrice()

        // Кнопка очистки корзины
        val clearCartButton: Button = findViewById(R.id.clearCartButton)
        clearCartButton.setOnClickListener {
            cartItems.clear()
            cartAdapter.notifyDataSetChanged()
            updateTotalPrice()
        }

        // Кнопка оформления заказа
        val checkoutButton: Button = findViewById(R.id.checkoutButton)
        checkoutButton.setOnClickListener {
            // Логика оформления заказа
        }
    }

    private fun updateTotalPrice() {
        val totalPrice = cartItems.sumByDouble { it.price * it.quantity }
        totalPriceText.text = "Итого: $totalPrice ₸"
    }
}
