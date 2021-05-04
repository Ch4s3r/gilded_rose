package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GildedRoseTest {

    @Test
    fun foo() {
        val expected = "foo"
        val items = arrayOf(
            Item(expected, 0, 0),
        )
        val app = GildedRose(items)

        app.updateQuality()

        val actual = app.items.first().name
        assertEquals(expected, actual)
    }
}
