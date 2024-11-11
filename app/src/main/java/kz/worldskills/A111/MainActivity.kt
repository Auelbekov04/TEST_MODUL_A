
package  kz.worldskills.A111
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Sms.Intents
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.worldskills.A111.CartFragment
import kz.worldskills.A111.CatalogFragment
import kz.worldskills.A111.ProfileFragment
import kz.worldskills.A111.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isLogin= getIntent().getBooleanExtra("isLogin",true);
        if (isLogin) {

            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)

        }
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

    // Функция для замены фрагментов
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
}
