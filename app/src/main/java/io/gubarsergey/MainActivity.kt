package io.gubarsergey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import io.gubarsergey.auth.ui.AuthFragment
import io.gubarsergey.auth.ui.AuthFragmentDirections
import org.koin.core.context.loadKoinModules

class MainActivity : AppCompatActivity(), RoutingOperations {

    private val navController by lazy {
        findNavController(R.id.fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}
