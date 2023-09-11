package com.yefri.data.models.relations.shopListProduct

import com.yefri.data.models.product.Products
import com.yefri.data.models.shopList.ShopLists
import org.jetbrains.exposed.dao.id.IntIdTable

object ShopListProducts: IntIdTable() {
    val shopListId = reference("shopListId", ShopLists)
    val productId = reference("productId", Products)
    val quantity = integer("quantity")
}