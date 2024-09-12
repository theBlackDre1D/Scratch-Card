package co.init.common.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.doInCoroutine(action: suspend () -> Unit): Job {
    return this.viewModelScope.launch {
        action.invoke()
    }
}

fun ViewModel.doInCoroutine(dispatcher: CoroutineDispatcher = Dispatchers.IO, action: suspend () -> Unit): Job {
    return this.viewModelScope.launch(dispatcher) {
        action.invoke()
    }
}

fun ViewModel.doInIOCoroutine(action: suspend () -> Unit): Job {
    return this.viewModelScope.launch(Dispatchers.IO) {
        action.invoke()
    }
}