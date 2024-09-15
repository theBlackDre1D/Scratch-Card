package co.init.scratchcardapp

import co.init.scratchcardapp.data.Card
import co.init.scratchcardapp.data.ScratchCardState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

class CardTest {

    @Test
    fun `canBeActivated should return true when cardState is NEEDS_ACTIVATION and cardNumber is not null`() {
        val card = Card(cardNumber = "12345", cardState = ScratchCardState.NEEDS_ACTIVATION)
        assertTrue(card.canBeActivated)
    }

    @Test
    fun `canBeActivated should return false when cardState is not NEEDS_ACTIVATION`() {
        val card = Card(cardNumber = "12345", cardState = ScratchCardState.INITIAL)
        assertFalse(card.canBeActivated)
    }

    @Test
    fun `canBeActivated should return false when cardNumber is null`() {
        val card = Card(cardNumber = null, cardState = ScratchCardState.NEEDS_ACTIVATION)
        assertFalse(card.canBeActivated)
    }

    @Test
    fun `isActivated should return true when cardState is ACTIVATED`() {
        val card = Card(cardState = ScratchCardState.ACTIVATED)
        assertTrue(card.isActivated)
    }

    @Test
    fun `isActivated should return false when cardState is not ACTIVATED`() {
        val card = Card(cardState = ScratchCardState.INITIAL)
        assertFalse(card.isActivated)
    }

    @Test
    fun `isScratched should return true when cardNumber is not null`() {
        val card = Card(cardNumber = "12345")
        assertTrue(card.isScratched)
    }

    @Test
    fun `isScratched should return false when cardNumber is null`() {
        val card = Card(cardNumber = null)
        assertFalse(card.isScratched)
    }

    @Test
    fun `generatePartNumber should return a valid UUID string`() {
        val partNumber = Card.generatePartNumber()
        assertDoesNotThrow {
            UUID.fromString(partNumber) // will throw IllegalArgumentException if not valid UUID
        }
    }
}
