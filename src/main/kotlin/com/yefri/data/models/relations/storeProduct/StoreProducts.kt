package com.yefri.data.models.relations.storeProduct

import com.yefri.data.models.product.Products
import com.yefri.data.models.store.Stores
import org.jetbrains.exposed.dao.id.IntIdTable

object StoreProducts : IntIdTable() {
    val storeId = reference("storeId", Stores)
    val productId = reference("productId", Products)
    val quantity = integer("quantity")
}