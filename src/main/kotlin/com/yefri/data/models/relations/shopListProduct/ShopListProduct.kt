package com.yefri.data.models.relations.shopListProduct

import com.yefri.data.models.product.Product
import com.yefri.data.models.shopList.ShopList
import com.yefri.routes.models.ShopListJsonProduct
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ShopListProduct(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ShopListProduct>(ShopListProducts)

    var shopList by ShopList referencedOn ShopListProducts.shopListId
    var product by Product referencedOn ShopListProducts.productId
    var quantity by ShopListProducts.quantity

    fun toJson() = ShopListJsonProduct(shopList.id.value.toString(),product.toJson(),quantity)
}