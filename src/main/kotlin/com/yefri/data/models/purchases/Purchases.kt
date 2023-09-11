package com.yefri.data.models.purchases

import com.yefri.data.models.product.Products
import com.yefri.data.models.user.Users
import org.jetbrains.exposed.dao.id.IntIdTable

object Purchases : IntIdTable() {
    val date = varchar("date", length = 250)
    val unds = integer("unds")
    val userId = reference("userId", Users)
    val productId = reference("productId", Products)
}