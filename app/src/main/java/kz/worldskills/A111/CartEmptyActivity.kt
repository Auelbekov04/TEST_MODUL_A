package kz.worldskills.A111

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CartEmptyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_empty)

        // Переход к каталогу
        val goToCatalogButton: Button = findViewById(R.id.goToCatalogButton)
        goToCatalogButton.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
            // Завершаем текущую активность, чтобы не вернуться к пустой корзине после перехода в каталог
            finish()
        }
    }
}
