package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ItemTest {
    @Test
    fun `toString of item concatenates name, sellIn and quality`() {
        val item = Item("name", 10, 15)

        val actual = item.toString()

        assertEquals("name, 10, 15", actual)
    }
}
