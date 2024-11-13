package uk.co.devfoundry.viewmodeltutorial.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CounterAcrossState(savedStateHandle: SavedStateHandle) : ViewModel() {

    // Using SavedStateHandle to persist the counter state
    private val _counter = savedStateHandle.getLiveData<Int>("counter", 0) // Default value is 0

    // Expose the counter as a getter for use in UI
    val counter: Int
        get() = _counter.value ?: 0

    // Method to increment the counter
    fun incrementCounter() {
        _counter.value = (_counter.value ?: 0) + 1
    }

    // Method to set the counter directly (for example, from UI or external logic)
    fun setCounter(value: Int) {
        _counter.value = value
    }
}
