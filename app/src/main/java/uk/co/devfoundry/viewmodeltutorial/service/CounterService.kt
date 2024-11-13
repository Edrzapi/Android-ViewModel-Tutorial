package uk.co.devfoundry.viewmodeltutorial.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class CounterService : Service() {

    private val binder = CounterBinder()
    private var counter = 0

    // Binder class to expose the service to clients
    inner class CounterBinder : Binder() {
        fun getService(): CounterService = this@CounterService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    // Method to retrieve the counter value
    fun getCounterValue(): Int {
        return counter++
    }
}