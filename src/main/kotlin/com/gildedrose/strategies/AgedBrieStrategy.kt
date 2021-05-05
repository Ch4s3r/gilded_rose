package com.gildedrose.strategies

import com.gildedrose.ItemNames
import com.gildedrose.MAX_QUALITY
import com.gildedrose.MIN_QUALITY
import com.gildedrose.Product
import com.gildedrose.expired

class AgedBrieStrategy : TestableUpdateStrategy {

    override fun test(product: Product) =
        product.name == ItemNames.AGED_BRIE

    override fun update(product: Product) =
        product.copy(
            quality = (product.quality + if (product.expired) 2 else 1).coerceIn(MIN_QUALITY, MAX_QUALITY),
            sellIn = product.sellIn - 1
        )
}
