package kz.worldskills.A111

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var editProfileButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.selectedItemId=R.id.navigation_profile;
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
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    finish()
                    true
                }
                R.id.navigation_profile -> {
//                    val intent = Intent(this, ProfileActivity::class.java)
//                    startActivity(intent)
//                    overridePendingTransition(0,0)
//                    finish()

                    true
                }
                else -> false
            }}
        // Инициализация элементов
        profileImage = findViewById(R.id.profileImage)
        profileName = findViewById(R.id.profileName)
        profileEmail = findViewById(R.id.profileEmail)
        editProfileButton = findViewById(R.id.editProfileButton)
        logoutButton = findViewById(R.id.logoutButton)

        // Загрузка данных пользователя
        loadUserData()

        // Обработка нажатия на кнопку редактирования профиля
        editProfileButton.setOnClickListener {
//            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Обработка нажатия на кнопку выхода
        logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun loadUserData() {
        // Здесь можно загрузить реальные данные пользователя
        profileName.text = "Иван Иванов"
        profileEmail.text = "ivan.ivanov@example.com"
        // Установите изображение для profileImage, если оно доступно
    }

    private fun logout() {
        // Логика выхода из аккаунта
        // Например, можно очистить данные пользователя и перейти на экран авторизации
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
