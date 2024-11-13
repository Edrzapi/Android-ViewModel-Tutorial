package uk.co.devfoundry.viewmodeltutorial.page

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import uk.co.devfoundry.viewmodeltutorial.service.CounterService
import uk.co.devfoundry.viewmodeltutorial.viewmodel.CounterAcrossState
import uk.co.devfoundry.viewmodeltutorial.viewmodel.CounterViewModel

class CounterActivity : ComponentActivity() {

    private lateinit var viewModel: CounterViewModel
    private lateinit var stateModel: CounterAcrossState

    private var counterService: CounterService? = null
    private var isBound = false

    // ServiceConnection to manage the connection lifecycle
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as CounterService.CounterBinder
            counterService = binder.getService()
            isBound = true

            // Access counter value from the bound service
            Log.d("MainActivity", "Counter Value: ${counterService?.getCounterValue()}")

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            counterService = null
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, CounterService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // View model initialize
        viewModel = ViewModelProvider(this).get(CounterViewModel::class.java)
        stateModel = ViewModelProvider(this).get(CounterAcrossState::class.java)
        enableEdgeToEdge()
        setContent {
            CounterFun(getCounterValue = { counterService?.getCounterValue() ?: 0 })
        }
    }

    @Composable
    fun CounterFun(getCounterValue: () -> Int) {

        val counter: Int = if (viewModel.counter != 0) {
            viewModel.counter
        } else {
            stateModel.counter
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(100.dp, 250.dp)
                .border(5.dp, color = Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Vertically center the buttons within Column
        ) {
            Text(text = "Counter value $counter")

            // First row: Start Count
            Row {

                Button(
                    onClick = {
                        viewModel.counter = getCounterValue()
                        stateModel.setCounter(viewModel.counter)
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Count")
                }
            }

            // Second row: Back
            Row {

                Button(
                    onClick = { finish() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Back")
                }
            }
        }
    }
}
