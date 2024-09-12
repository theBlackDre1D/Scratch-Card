package co.init.scratchcardapp

import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.util.UUID

class UUIDTest {

    @Test
    fun testUUIDFormat() {
        val uuidString = UUID.randomUUID().toString()
        val uuidRegex = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$".toRegex()
        assertEquals(true, uuidRegex.matches(uuidString))
    }

    @Test
    fun testUUIDUniqueness() {
        val uuid1 = UUID.randomUUID().toString()
        val uuid2 = UUID.randomUUID().toString()
        assertNotEquals(uuid1, uuid2)
    }
}