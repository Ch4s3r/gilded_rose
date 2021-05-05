package com.gildedrose.strategies

import com.gildedrose.ItemNames
import com.gildedrose.Product

class SulfurasStrategy : TestableUpdateStrategy {

    override fun test(product: Product) = product.name == ItemNames.SULFURAS

    override fun update(product: Product) = product
}
