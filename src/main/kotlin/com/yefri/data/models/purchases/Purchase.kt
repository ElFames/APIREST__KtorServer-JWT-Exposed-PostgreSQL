package com.yefri.data.models.purchases

import com.yefri.data.models.product.Product
import com.yefri.data.models.user.User
import com.yefri.routes.models.JsonPurchase
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Purchase(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Purchase>(Purchases)
    var date by Purchases.date
    var unds by Purchases.unds
    var product by Product referencedOn Purchases.productId
    var user by User referencedOn Purchases.userId

    fun toJsonPurchase() = JsonPurchase(
        date,
        unds,
        product.toJson(),
        user.username
    )
}