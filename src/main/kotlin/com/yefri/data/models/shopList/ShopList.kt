package com.yefri.data.models.shopList

import com.yefri.data.models.relations.shopListProduct.ShopListProduct
import com.yefri.data.models.relations.shopListProduct.ShopListProducts
import com.yefri.data.models.user.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ShopList(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ShopList>(ShopLists)

    var shopListName by ShopLists.shopListName
    var user by User referencedOn ShopLists.userId
    val products get() = ShopListProduct.find { ShopListProducts.shopListId eq id }.toList()
    fun contains(product: com.yefri.data.models.product.Product): Boolean {
        return products.any { it.product == product }
    }
}