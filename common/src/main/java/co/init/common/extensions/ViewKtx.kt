package co.init.common.extensions

import android.os.SystemClock
import android.view.View


private const val CLICK_DELAY: Long = 750

/*
* On click with double tap handled
*/
fun View.onClickDebounce(onClick: (View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private val lastInteraction = LastInteraction()

        override fun onClick(v: View) = debounce(CLICK_DELAY, lastInteraction) {
            onClick(v)
        }
    })
}

class LastInteraction(var ms: Long = 0) {
    fun update() { ms = SystemClock.elapsedRealtime()}
}

fun debounce(debounceTimerMs: Long, lastInteraction: LastInteraction, action: () -> Unit) {
    if (SystemClock.elapsedRealtime() - lastInteraction.ms < debounceTimerMs) {
        return
    } else {
        action()
    }
    lastInteraction.update()
}