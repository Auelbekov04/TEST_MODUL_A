package kz.worldskills.A111

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var backButton: Button
    private lateinit var showHidePassword: Button
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameInput = findViewById(R.id.usernameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        registerButton = findViewById(R.id.registerButton)
        backButton = findViewById(R.id.backButton)
        showHidePassword = findViewById(R.id.showHidePassword)

        // Обработка кнопки регистрации
        registerButton.setOnClickListener {
            registerUser()
        }

        // Обработка кнопки "Назад"
        backButton.setOnClickListener {
            finish() // Закрытие активности и возврат на предыдущую страницу
        }

        // Обработка показа/скрытия пароля
        showHidePassword.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    private fun registerUser() {
        val username = usernameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        // Проверка на пустые поля
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        // Проверка корректности почты
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Введите корректную почту", Toast.LENGTH_SHORT).show()
            return
        }
        val intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
        return
        // Запрос на сервер для регистрации
        val client = OkHttpClient()
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        val requestBody=jsonObject.toString().toRequestBody("application/json".toMediaType())
//        val requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        val request = Request.Builder()
            .url("https://your-api-url.com/api/register")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Успешная регистрация, переход в каталог
                    val intent = Intent(this@RegisterActivity, CatalogActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    // Функция для показа/скрытия пароля
    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            showHidePassword.text = "Показать"
        } else {
            passwordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            showHidePassword.text = "Скрыть"
        }
        isPasswordVisible = !isPasswordVisible
        passwordInput.setSelection(passwordInput.text.length) // Курсор в конце текста
    }
}
