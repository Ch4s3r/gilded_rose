package com.gildedrose

import com.gildedrose.ItemNames.AGED_BRIE
import com.gildedrose.ItemNames.SULFURAS

const val MIN_QUALITY = 0
const val MAX_QUALITY = 50

data class Product(
    val name: String,
    val sellIn: Int,
    val quality: Int,
)

private val Product.expired
    get() = this.sellIn <= 0

fun Item.toProduct() =
    Product(
        name = this.name,
        sellIn = this.sellIn,
        quality = this.quality,
    )

fun Product.toItem() =
    Item(
        name = this.name,
        sellIn = this.sellIn,
        quality = this.quality,
    )

interface TestableUpdateStrategy : UpdateStrategy, Testable

interface UpdateStrategy {
    fun update(product: Product): Product
}

interface Testable {
    fun test(product: Product): Boolean
}

class AgedBrieStrategy : TestableUpdateStrategy {

    override fun test(product: Product) =
        product.name == AGED_BRIE

    override fun update(product: Product) =
        product.copy(
            quality = (product.quality + if (product.expired) 2 else 1).coerceIn(MIN_QUALITY, MAX_QUALITY),
            sellIn = product.sellIn - 1
        )
}

class BackStagePassStrategy : TestableUpdateStrategy {

    override fun test(product: Product) =
        product.name.startsWith("Backstage passes")

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

class SulfurasStrategy : TestableUpdateStrategy {

    override fun test(product: Product) = product.name == SULFURAS

    override fun update(product: Product) = product
}

class DefaultUpdateStrategy : UpdateStrategy {

    override fun update(product: Product) =
        product.copy(
            quality = (product.quality - if (product.expired) 2 else 1).coerceIn(MIN_QUALITY, MAX_QUALITY),
            sellIn = product.sellIn - 1
        )
}

class GildedRose(var items: Array<Item>) {

    private val strategies = listOf<TestableUpdateStrategy>(
        AgedBrieStrategy(),
        BackStagePassStrategy(),
        SulfurasStrategy(),
    )

    private val defaultUpdateStrategy = DefaultUpdateStrategy()

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
