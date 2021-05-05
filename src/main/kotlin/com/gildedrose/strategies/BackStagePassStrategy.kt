package com.gildedrose.strategies

import com.gildedrose.BACKSTAGE_PASSES
import com.gildedrose.MAX_QUALITY
import com.gildedrose.MIN_QUALITY
import com.gildedrose.Product
import com.gildedrose.expired

class BackStagePassStrategy : TestableUpdateStrategy {

    override fun test(product: Product) =
        product.name.startsWith(BACKSTAGE_PASSES)

    override fun update(product: Product): Product {
        val quality = when {
            product.expired -> 0
            product.sellIn < 6 -> product.quality + 3
            product.sellIn < 11 -> product.quality + 2
            else -> product.quality + 1
        }.coerceIn(MIN_QUALITY, MAX_QUALITY)
        return product.copy(
            quality = quality,
            sellIn = product.sellIn - 1
        )
    }
}
