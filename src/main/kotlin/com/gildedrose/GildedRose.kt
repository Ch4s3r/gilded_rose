package com.gildedrose

import com.gildedrose.ItemNames.AGED_BRIE
import com.gildedrose.ItemNames.BACKSTAGE_PASS_TAFKAL
import com.gildedrose.ItemNames.SULFURAS

const val MAX_QUALITY = 50

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        items = items
            .map(Item::toProduct)
            .map(this::updateProduct)
            .map(Product::toItem)
            .toTypedArray()
    }

    private fun updateProduct(product: Product): Product {
        var product = product
        if (product.name != AGED_BRIE && product.name != BACKSTAGE_PASS_TAFKAL) {
            if (product.quality > 0) {
                if (product.name != SULFURAS) {
                    product = product.copy(quality = product.quality - 1)
                }
            }
        } else {
            if (product.quality < MAX_QUALITY) {
                product = product.copy(quality = product.quality + 1)

                if (product.name == BACKSTAGE_PASS_TAFKAL) {
                    if (product.sellIn < 11) {
                        if (product.quality < MAX_QUALITY) {
                            product = product.copy(quality = product.quality + 1)
                        }
                    }

                    if (product.sellIn < 6) {
                        if (product.quality < MAX_QUALITY) {
                            product = product.copy(quality = product.quality + 1)
                        }
                    }
                }
            }
        }

        if (product.name != SULFURAS) {
            product = product.copy(sellIn = product.sellIn - 1)
        }

        if (product.sellIn < 0) {
            if (product.name != AGED_BRIE) {
                if (product.name != BACKSTAGE_PASS_TAFKAL) {
                    if (product.quality > 0) {
                        if (product.name != SULFURAS) {
                            product = product.copy(quality = product.quality - 1)
                        }
                    }
                } else {
                    product = product.copy(quality = 0)
                }
            } else {
                if (product.quality < MAX_QUALITY) {
                    product = product.copy(quality = product.quality + 1)
                }
            }
        }
        return product
    }
}
