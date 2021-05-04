package com.gildedrose

import com.gildedrose.ItemNames.AGED_BRIE
import com.gildedrose.ItemNames.BACKSTAGE_PASS_TAFKAL
import com.gildedrose.ItemNames.CONJURED_CAKE
import com.gildedrose.ItemNames.SULFURAS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

const val ANY_ITEM_NAME = "name"
const val ANY_SELLIN = 10
const val ANY_QUALITY = 10

class GildedRoseTest {

    private fun assertEquals(expected: Item, actual: Item) {
        assertEquals(expected.name, actual.name)
        assertEquals(expected.sellIn, actual.sellIn)
        assertEquals(expected.quality, actual.quality)
    }

    @Test
    fun `items reduce sellIn value by one each day`() {
        val item = Item(ANY_ITEM_NAME, 5, ANY_QUALITY)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(4, app.items.first().sellIn)
    }

    @Test
    fun `items reduce quality value by one each day`() {
        val item = Item(ANY_ITEM_NAME, ANY_SELLIN, 5)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(4, app.items.first().quality)
    }

    @Test
    fun `Once the sell by date has passed, Quality degrades twice as fast`() {
        val item = Item(ANY_ITEM_NAME, 0, 6)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(4, app.items.first().quality)
    }

    @Test
    fun `The Quality of an item is never negative`() {
        val item = Item(ANY_ITEM_NAME, 0, 0)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(0, app.items.first().quality)
    }

    @ParameterizedTest
    @CsvSource("0", "-1", "-10", "-50")
    fun `'Aged Brie' increases quality by 2 if sellin is decreased to lower than 0`(sellIn: Int) {
        val item = Item(AGED_BRIE, sellIn, 0)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(2, app.items.first().quality)
    }

    @ParameterizedTest
    @CsvSource("1", "2", "10", "50")
    fun `'Aged Brie' actually increases by 1 in Quality if increased to 0 or higher`(sellIn: Int) {
        val item = Item(AGED_BRIE, sellIn, 0)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(1, app.items.first().quality)
    }

    @ParameterizedTest
    @CsvSource("49", "50")
    fun `The Quality of an item is never more than 50`(quality: Int) {
        val item = Item(AGED_BRIE, 0, quality)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(50, app.items.first().quality)
    }

    @Test
    fun `'Sulfuras' never decreases in Quality`() {
        val item = Item(SULFURAS, ANY_SELLIN, 80)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(80, app.items.first().quality)
    }

    @Test
    fun `'Sulfuras' never has to be sold`() {
        val item = Item(SULFURAS, 80, ANY_QUALITY)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(80, app.items.first().sellIn)
    }

    @ParameterizedTest
    @CsvSource("11", "12", "50")
    fun `Backstage pass quality increases by 1 when there are 11 days or more`(sellIn: Int) {
        val item = Item(BACKSTAGE_PASS_TAFKAL, sellIn, 20)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(21, app.items.first().quality)
    }

    @ParameterizedTest
    @CsvSource("6", "7", "8", "9", "10")
    fun `Backstage pass quality increases by 2 when there are 10 days or less`(sellIn: Int) {
        val item = Item(BACKSTAGE_PASS_TAFKAL, sellIn, 20)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(22, app.items.first().quality)
    }

    @ParameterizedTest
    @CsvSource("1", "2", "3", "4", "5")
    fun `Backstage pass quality increases by 3 when there are 5 days or less`(sellIn: Int) {
        val item = Item(BACKSTAGE_PASS_TAFKAL, sellIn, 20)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(23, app.items.first().quality)
    }

    @Test
    fun `Backstage pass quality drops to 0 after the concert`() {
        val item = Item(BACKSTAGE_PASS_TAFKAL, 0, 20)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(0, app.items.first().quality)
    }

    @Test
    @Disabled
    fun `'Conjured' items degrade in Quality twice as fast as normal items`() {
        val item = Item(CONJURED_CAKE, ANY_SELLIN, 20)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(18, app.items.first().quality)
    }

    @ParameterizedTest
    @CsvSource(
        "5, 48,50",
        "5, 49,50",
        "5, 50,50",
        "10,48,50",
        "10,49,50",
        "10,50,50",
        "15,48,49",
        "15,49,50",
        "15,50,50",
    )
    fun `Backstage pass edge case`(sellIn: Int, quality: Int, expectedQuality: Int) {
        val item = Item(BACKSTAGE_PASS_TAFKAL, sellIn, quality)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertEquals(expectedQuality, app.items.first().quality)
    }
}
