package com.gildedrose

data class Product(
    val name: String,
    val sellIn: Int,
    val quality: Int,
)

val Product.expired
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
