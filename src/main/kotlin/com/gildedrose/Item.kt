package com.gildedrose

open class Item(var name: String, var sellIn: Int, var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }
}

data class Product(
    private val item: Item,
) {
    val name by item::name
    val sellIn by item::sellIn
    val quality by item::quality

    override fun toString() = item.toString()
}
