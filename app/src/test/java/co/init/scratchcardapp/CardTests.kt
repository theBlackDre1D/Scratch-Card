package co.init.scratchcardapp

import co.init.scratchcardapp.data.Card
import co.init.scratchcardapp.data.ScratchCardState
import org.junit.Assert.*
import org.junit.Test
import java.util.UUID

class CardTest {

    @Test
    fun testCardDefaultValues() {
        val card = Card()
        assertNull(card.cardNumber)
        assertEquals(ScratchCardState.INITIAL, card.cardState)
        assertFalse(card.canBeActivated)
        assertFalse(card.isActivated)
        assertFalse(card.isScratched)
    }

    @Test
    fun testCardWithCardNumber() {
        val card = Card(cardNumber = "1234567890")
        assertEquals("1234567890", card.cardNumber)
        assertEquals(ScratchCardState.INITIAL, card.cardState)
        assertFalse(card.canBeActivated)
        assertFalse(card.isActivated)
        assertTrue(card.isScratched)
    }

    @Test
    fun testCanBeActivated() {
        val card = Card(cardNumber = "1234567890", cardState = ScratchCardState.NEEDS_ACTIVATION)
        assertTrue(card.canBeActivated)
    }

    @Test
    fun testCannotBeActivatedWithoutCardNumber() {
        val card = Card(cardState = ScratchCardState.NEEDS_ACTIVATION)
        assertFalse(card.canBeActivated)
    }

    @Test
    fun testIsActivated() {
        val card = Card(cardNumber = "1234567890", cardState = ScratchCardState.ACTIVATED)
        assertTrue(card.isActivated)
    }

    @Test
    fun testGeneratePartNumber() {
        val partNumber = Card.generatePartNumber()
        assertNotNull(partNumber)
        assertTrue(partNumber.isNotEmpty())
        // Ensure it generates a UUID
        UUID.fromString(partNumber) // This will throw an exception if it's not a valid UUID
    }
}

