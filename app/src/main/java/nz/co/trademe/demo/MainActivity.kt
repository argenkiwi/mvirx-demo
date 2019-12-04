package nz.co.trademe.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (savedInstanceState) {
            null -> supportFragmentManager.beginTransaction()
                .add(android.R.id.content, MainFragment())
                .commit()
        }
    }
}
