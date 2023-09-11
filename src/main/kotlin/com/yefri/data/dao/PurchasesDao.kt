package com.yefri.data.dao

import com.yefri.data.models.purchases.Purchase
import com.yefri.data.models.purchases.Purchases
import com.yefri.data.models.user.User
import com.yefri.routes.models.JsonPurchase
import org.jetbrains.exposed.sql.transactions.transaction

class PurchasesDao {
    fun addNewPurchase(purchase: JsonPurchase, user: User) {
        transaction {
            val product = com.yefri.data.models.product.Product.find { com.yefri.data.models.product.Products.code eq purchase.product.code}.firstOrNull()
            Purchase.new {
                date = purchase.date
                unds = purchase.unds
                this.product = product!!
                this.user = user
            }
        }
    }
    fun getUserPurchases(user: User): MutableList<JsonPurchase> {
        val userPurchases = mutableListOf<JsonPurchase>()
        transaction {
            val purchases = Purchase.find { Purchases.userId eq user.id }.toList()
            purchases.forEach { userPurchases.add(it.toJsonPurchase()) }
        }
        return userPurchases
    }
    fun getAllPurchases(user: User): MutableList<JsonPurchase> {
        val allPurchases = mutableListOf<JsonPurchase>()
        val users = mutableListOf<User>()
        transaction {
            val storeId = user.store().id
            User.all().forEach {
                if (it.store().id == storeId) users.add(it)
            }
            users.forEach {
                val purchases = Purchase.find { Purchases.userId eq it.id }.toList()
                purchases.forEach { allPurchases.add(it.toJsonPurchase())}
            }
        }
        return allPurchases
    }
}