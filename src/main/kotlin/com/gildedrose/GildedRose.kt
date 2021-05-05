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

private fun Product.increaseQuality() =
    this.copy(quality = if (quality < MAX_QUALITY) quality + 1 else quality)

private fun Product.decreaseQuality() =
    this.copy(quality = if (quality > MIN_QUALITY) quality - 1 else quality)

private val Product.expired
    get() = this.sellIn < 0

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

    override fun update(product: Product): Product {
        var product = product.increaseQuality()
        product = product.copy(sellIn = product.sellIn - 1)
        if (product.expired)
            product = product.increaseQuality()
        return product
    }
}

class BackStagePassStrategy : TestableUpdateStrategy {

    override fun test(product: Product) =
        product.name.startsWith("Backstage passes")

    override fun update(product: Product): Product {
        var product = product.increaseQuality()
        val qualityIncrease = when {
            product.sellIn < 6 -> 2
            product.sellIn < 11 -> 1
            else -> 0
        }
        repeat(qualityIncrease) {
            product = product.increaseQuality()
        }
        product = product.copy(sellIn = product.sellIn - 1)
        if (product.expired)
            product = product.copy(quality = 0)
        return product
    }
}

class SulfurasStrategy : TestableUpdateStrategy {

    override fun test(product: Product) =
        product.name == SULFURAS

    override fun update(product: Product): Product {
        return product
    }
}

class DefaultUpdateStrategy : UpdateStrategy {

    override fun update(product: Product): Product {
        var product = product.decreaseQuality()
        product = product.copy(sellIn = product.sellIn - 1)
        if (product.expired)
            product = product.decreaseQuality()
        return product
    }
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
