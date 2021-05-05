package com.gildedrose.strategies

import com.gildedrose.CONJURED_PREFIX
import com.gildedrose.MAX_QUALITY
import com.gildedrose.MIN_QUALITY
import com.gildedrose.Product
import com.gildedrose.expired

class ConjuredStrategy : TestableUpdateStrategy {

    override fun test(product: Product) =
        product.name.startsWith(CONJURED_PREFIX)

    override fun update(product: Product) =
        product.copy(
            quality = (product.quality - if (product.expired) 4 else 2).coerceIn(MIN_QUALITY, MAX_QUALITY),
            sellIn = product.sellIn - 1
        )
}
