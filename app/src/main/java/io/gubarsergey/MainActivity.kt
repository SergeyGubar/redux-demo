package io.gubarsergey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.gubarsergey.auth.ui.AuthFragment
import io.gubarsergey.auth.ui.AuthFragmentDirections
import org.koin.core.context.loadKoinModules

interface BottomBarController {
    fun showBottomBar()
    fun hideBottomBar()
}

class MainActivity : AppCompatActivity(), RoutingOperations, BottomBarController {

    private val navController by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_container)!!.findNavController()
    }

    private val bottomBar by lazy {
        findViewById<BottomNavigationView>(R.id.bottom_nav)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupWithNavController(bottomBar, navController)
        Router.routingOperations = this
    }

    override fun goTo(destination: Screen) {
        runOnUiThread {
            when (destination) {
                Screen.CustomerOrders -> goToCustomerOrders()
            }
        }
    }

    private fun goToCustomerOrders() {
        navController.navigate(AuthFragmentDirections.goToCustomerOrders())
    }

    override fun showBottomBar() {
        bottomBar.show()
    }

    override fun hideBottomBar() {
        bottomBar.hide()
    }
}
