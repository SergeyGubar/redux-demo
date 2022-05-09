package io.gubarsergey

import android.graphics.Color
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.seismic.ShakeDetector
import io.gubarsergey.artists.ui.AvailableArtistsFragmentDirections
import io.gubarsergey.auth.ui.AuthFragmentDirections
import io.gubarsergey.guards.GuardAction
import io.gubarsergey.orders.OrdersFragmentDirections
import io.gubarsergey.redux.operations.Command
import timber.log.Timber
import java.security.Guard

interface BottomBarController {
    fun showBottomBar()
    fun hideBottomBar()
}

class MainActivity : AppCompatActivity(), RoutingOperations, BottomBarController, ShakeDetector.Listener {

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
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)
        sd.start(sensorManager)
    }

    override fun goTo(destination: Screen) {

        runOnUiThread {
            when (destination) {
                Screen.CustomerOrders -> navController.navigate(AuthFragmentDirections.goToCustomerOrders())
                Screen.CreateOrder -> navController.navigate(AvailableArtistsFragmentDirections.goToCreateOrder())
                Screen.OrderConfirmation -> navController.navigate(OrdersFragmentDirections.openOrderConfirmation())
                Screen.SendOrderResult -> navController.navigate(OrdersFragmentDirections.openSendOrderResult())
                Screen.Login -> navController.popBackStack(R.id.authFragment, false)
            }
        }
    }

    override fun goBack() {
        navController.popBackStack()
    }

    override fun showBottomBar() {
        bottomBar.show()
    }

    override fun hideBottomBar() {
        bottomBar.hide()
    }

    override fun hearShake() {
        if (supportFragmentManager.findFragmentByTag("MYTAG") == null) {
            TimeTravelSheet().show(supportFragmentManager, "MYTAG")
        }
    }

    class TimeTravelSheet : BottomSheetDialogFragment() {

        private val adapter = ReduxActionsAdapter()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.bottom_sheet_time_travel, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val recycler = view.findViewById<RecyclerView>(R.id.actionsRecycler)
            recycler.adapter = adapter
            recycler.verticalLinearLayoutManager()
            recycler.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter.submitList(
                ReduxDemoApp.core.store.actionHistory.map {
                    ReduxActionProps(
                        id = it.id,
                        timeStamp = it.timeStamp.toString(),
                        name = it.action.toString(),
                        apply = Command {
                            ReduxDemoApp.core.store.setState(it.state)
                            dismiss()
                        }
                    )
                }.reversed()
            )

            ReduxDemoApp.core.store.actionHistory.filter { it.action is GuardAction }.forEachIndexed { index, element ->
                val guardErrorTextView = TextView(context)
                guardErrorTextView.text =
                    "${index + 1}. GUARD ${element.action.javaClass.simpleName} worked.\n${(element.action as GuardAction).description()}"

                guardErrorTextView.setTextColor(Color.RED)
                view.findViewById<LinearLayout>(R.id.guardsActions).addView(
                    guardErrorTextView
                )
            }

            view.findViewById<Button>(R.id.clearButton).setOnClickListener {
                adapter.submitList(listOf())
                ReduxDemoApp.core.store.clearHistory()
            }
        }
    }
}


