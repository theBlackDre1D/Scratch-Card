package co.init.scratchcardapp.data

import java.io.Serializable
import java.util.UUID

data class Card(
    val cardNumber: String? = null,
    val cardState: ScratchCardState = ScratchCardState.INITIAL
) : Serializable {

    companion object {
        fun generatePartNumber(): String = UUID.randomUUID().toString()
    }

    val canBeActivated: Boolean
        get() = this.cardState == ScratchCardState.NEEDS_ACTIVATION && this.cardNumber != null

    val isActivated: Boolean
        get() = this.cardState == ScratchCardState.ACTIVATED

    val isScratched: Boolean
        get() = cardNumber != null
}
