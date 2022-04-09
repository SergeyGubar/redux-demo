package io.gubarsergey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.gubarsergey.auth.ui.AuthFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.inTransaction {
            add(R.id.fragment_container, AuthFragment())
        }
    }
}
