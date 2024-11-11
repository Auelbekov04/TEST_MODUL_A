package kz.worldskills.A111

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Получаем элементы интерфейса
        val productImage: ImageView = findViewById(R.id.productImage)
        val productName: TextView = findViewById(R.id.productName)
        val productDescription: TextView = findViewById(R.id.productDescription)
        val addToCartButton: Button = findViewById(R.id.addToCartButton)

        // Получаем данные о товаре из интента
        val product = intent.getSerializableExtra("product") as? Product
        product?.let {
            productName.text = it.name
            productDescription.text = it.description

            // Загрузка изображения (используем Glide для упрощения)
            Glide.with(this)
                .load(it.imageUrl)
                .into(productImage)
        }

        // Обработка нажатия на кнопку "Добавить в корзину"
        addToCartButton.setOnClickListener {
            // Здесь можно добавить товар в корзину
            // Показ сообщения для подтверждения
            Snackbar.make(it, "Товар добавлен в корзину", Snackbar.LENGTH_LONG).show()
        }
    }
}
