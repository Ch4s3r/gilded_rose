package com.gildedrose

import com.gildedrose.ItemNames.AGED_BRIE
import com.gildedrose.ItemNames.BACKSTAGE_PASS_TAFKAL
import com.gildedrose.ItemNames.SULFURAS

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        for (i in items.indices) {
            val current = items[i]
            if (current.name != AGED_BRIE && current.name != BACKSTAGE_PASS_TAFKAL) {
                if (current.quality > 0) {
                    if (current.name != SULFURAS) {
                        current.quality = current.quality - 1
                    }
                }
            } else {
                if (current.quality < 50) {
                    current.quality = current.quality + 1

                    if (current.name == BACKSTAGE_PASS_TAFKAL) {
                        if (current.sellIn < 11) {
                            if (current.quality < 50) {
                                current.quality = current.quality + 1
                            }
                        }

                        if (current.sellIn < 6) {
                            if (current.quality < 50) {
                                current.quality = current.quality + 1
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
                        current.quality = current.quality - current.quality
                    }
                } else {
                    if (current.quality < 50) {
                        current.quality = current.quality + 1
                    }
                }
            }
        }
    }
}
