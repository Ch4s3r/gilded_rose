package com.gildedrose

open class Item(var name: String, var sellIn: Int, var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }
}

data class Product(
    val name: String,
    val sellIn: Int,
    val quality: Int,
)

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
