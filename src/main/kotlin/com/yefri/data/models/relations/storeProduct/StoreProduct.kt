package com.yefri.data.models.relations.storeProduct

import com.yefri.data.models.store.Store
import com.yefri.routes.models.StoreJsonProduct
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class StoreProduct(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<StoreProduct>(StoreProducts)
    var store by Store referencedOn StoreProducts.storeId
    var product by com.yefri.data.models.product.Product referencedOn StoreProducts.productId
    var quantity by StoreProducts.quantity

    fun toJson() = StoreJsonProduct(store.id.value.toString(),product.toJson(),quantity)
}