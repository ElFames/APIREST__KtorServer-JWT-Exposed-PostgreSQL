package com.yefri.data.models.store

import com.yefri.data.models.product.Product
import com.yefri.data.models.relations.storeProduct.StoreProduct
import com.yefri.data.models.relations.storeProduct.StoreProducts
import com.yefri.data.models.relations.userStores.UserStores
import com.yefri.data.models.relations.userStores.UsersStores
import com.yefri.data.models.user.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Store(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Store>(Stores)
    var storeName by Stores.storeName
    var user by User referencedOn Stores.userId
    val products get() = StoreProduct.find { StoreProducts.storeId eq id }.toList()
    val usersHost get() = UserStores.find { UsersStores.storeId eq id }.toList()

    fun contains(product: Product): Boolean {
        return products.any { it.product == product }
    }
}