package com.gildedrose

import com.gildedrose.ItemNames.AGED_BRIE
import com.gildedrose.ItemNames.BACKSTAGE_PASS_TAFKAL
import com.gildedrose.ItemNames.SULFURAS

const val MAX_QUALITY = 50

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        items.forEach { current ->
            if (current.name != AGED_BRIE && current.name != BACKSTAGE_PASS_TAFKAL) {
                if (current.quality > 0) {
                    if (current.name != SULFURAS) {
                        current.quality = current.quality - 1
                    }
                }
            } else {
                if (current.quality < MAX_QUALITY) {
                    current.quality += 1

                    if (current.name == BACKSTAGE_PASS_TAFKAL) {
                        if (current.sellIn < 11) {
                            if (current.quality < MAX_QUALITY) {
                                current.quality += 1
                            }
                        }

                        if (current.sellIn < 6) {
                            if (current.quality < MAX_QUALITY) {
                                current.quality += 1
                            }
                        }
                    }
                }
            }

            if (current.name != SULFURAS) {
                current.sellIn = current.sellIn - 1
            }

            if (current.sellIn < 0) {
                if (current.name != AGED_BRIE) {
                    if (current.name != BACKSTAGE_PASS_TAFKAL) {
                        if (current.quality > 0) {
                            if (current.name != SULFURAS) {
                                current.quality = current.quality - 1
                            }
                        }
                    } else {
                        current.quality = 0
                    }
                } else {
                    if (current.quality < MAX_QUALITY) {
                        current.quality += 1
                    }
                }
            }
        }
    }
}
