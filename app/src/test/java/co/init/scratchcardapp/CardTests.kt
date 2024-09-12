package co.init.scratchcardapp

import co.init.scratchcardapp.data.Card
import co.init.scratchcardapp.data.ScratchCardState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Assert.assertNotEquals
import org.junit.Test


class CardTest {

    @Test
    fun `testDefaultConstructor`() {
        val card = Card()

        assertNull(card.cardNumber)
        assertEquals(ScratchCardState.INITIAL, card.cardState)
    }

    @Test
    fun `testParameterizedConstructor`() {
        val cardNumber = "1234567890"
        val cardState = ScratchCardState.INITIAL

        val card = Card(cardNumber, cardState)

        assertEquals(cardNumber, card.cardNumber)
        assertEquals(cardState, card.cardState)
    }

    @Test
    fun `testCopyConstructor`() {
        val originalCard = Card("1234567890", ScratchCardState.ACTIVATED)
        val copyCard = originalCard.copy()

        assertEquals(originalCard.cardNumber, copyCard.cardNumber)
        assertEquals(originalCard.cardState, copyCard.cardState)
    }

    @Test
    fun `testEqualsAndHashCode`() {
        val card1 = Card("1234567890", ScratchCardState.ACTIVATED)
        val card2 = Card("1234567890", ScratchCardState.ACTIVATED)
        val card3 = Card("9876543210", ScratchCardState.INITIAL)

        assertEquals(card1, card2)
        assertNotEquals(card1, card3)
        assertEquals(card1.hashCode(), card2.hashCode())
        assertNotEquals(card1.hashCode(), card3.hashCode())
    }

    @Test
    fun `testToString`() {
        val card = Card("1234567890", ScratchCardState.ACTIVATED)
        val expectedString = "Card(cardNumber=1234567890, cardState=ACTIVATED)"

        assertEquals(expectedString, card.toString())
    }
}