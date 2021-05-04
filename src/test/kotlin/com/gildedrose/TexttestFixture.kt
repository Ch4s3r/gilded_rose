package com.gildedrose

import com.gildedrose.ItemNames.AGED_BRIE
import com.gildedrose.ItemNames.BACKSTAGE_PASS_TAFKAL
import com.gildedrose.ItemNames.CONJURED_CAKE
import com.gildedrose.ItemNames.DEXTERITY_VEST
import com.gildedrose.ItemNames.ELIXIR
import com.gildedrose.ItemNames.SULFURAS

fun main(args: Array<String>) {

    val items = arrayOf(
        Item(DEXTERITY_VEST, 10, 20),
        Item(AGED_BRIE, 2, 0),
        Item(ELIXIR, 5, 7),
        Item(SULFURAS, 0, 80),
        Item(SULFURAS, -1, 80),
        Item(BACKSTAGE_PASS_TAFKAL, 15, 20),
        Item(BACKSTAGE_PASS_TAFKAL, 10, 49),
        Item(BACKSTAGE_PASS_TAFKAL, 5, 49),
        Item(CONJURED_CAKE, 3, 6)
    )

    val app = GildedRose(items)

    var days = 2
    if (args.isNotEmpty()) {
        days = Integer.parseInt(args[0]) + 1
    }

    for (i in 0 until days) {
        println("-------- day $i --------")
        println("name, sellIn, quality")
        for (item in items) {
            println(item)
        }
        println()
        app.updateQuality()
    }
}
