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
}
