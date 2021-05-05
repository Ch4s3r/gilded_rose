package com.gildedrose.strategies

import com.gildedrose.Product

interface TestableUpdateStrategy : UpdateStrategy, Testable
interface UpdateStrategy {
    fun update(product: Product): Product
}

interface Testable {
    fun test(product: Product): Boolean
}
