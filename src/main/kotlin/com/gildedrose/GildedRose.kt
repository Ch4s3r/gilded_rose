package com.gildedrose

import com.gildedrose.strategies.AgedBrieStrategy
import com.gildedrose.strategies.BackStagePassStrategy
import com.gildedrose.strategies.ConjuredStrategy
import com.gildedrose.strategies.DefaultUpdateStrategy
import com.gildedrose.strategies.SulfurasStrategy

private val strategies = listOf(
    AgedBrieStrategy(),
    BackStagePassStrategy(),
    SulfurasStrategy(),
    ConjuredStrategy(),
)

private val defaultUpdateStrategy = DefaultUpdateStrategy()

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        items = items
            .map(Item::toProduct)
            .map { product ->
                (strategies.firstOrNull { it.test(product) } ?: defaultUpdateStrategy).update(product)
            }
            .map(Product::toItem)
            .toTypedArray()
    }
}
