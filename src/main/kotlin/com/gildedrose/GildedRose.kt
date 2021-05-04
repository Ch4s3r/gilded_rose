package com.gildedrose

import com.gildedrose.ItemNames.AGED_BRIE
import com.gildedrose.ItemNames.BACKSTAGE_PASS_TAFKAL
import com.gildedrose.ItemNames.SULFURAS

const val MAX_QUALITY = 50

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        items.forEach { updateItem(it) }
    }

    private fun updateItem(item: Item) {
        if (item.name != AGED_BRIE && item.name != BACKSTAGE_PASS_TAFKAL) {
            if (item.quality > 0) {
                if (item.name != SULFURAS) {
                    item.quality = item.quality - 1
                }
            }
        } else {
            if (item.quality < MAX_QUALITY) {
                item.quality += 1

                if (item.name == BACKSTAGE_PASS_TAFKAL) {
                    if (item.sellIn < 11) {
                        if (item.quality < MAX_QUALITY) {
                            item.quality += 1
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < MAX_QUALITY) {
                            item.quality += 1
                        }
                    }
                }
            }
        }

        if (item.name != SULFURAS) {
            item.sellIn = item.sellIn - 1
        }

        if (item.sellIn < 0) {
            if (item.name != AGED_BRIE) {
                if (item.name != BACKSTAGE_PASS_TAFKAL) {
                    if (item.quality > 0) {
                        if (item.name != SULFURAS) {
                            item.quality = item.quality - 1
                        }
                    }
                } else {
                    item.quality = 0
                }
            } else {
                if (item.quality < MAX_QUALITY) {
                    item.quality += 1
                }
            }
        }
    }
}
