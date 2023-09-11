package com.yefri.data.models.user

import com.yefri.data.models.relations.userStores.UserStores
import com.yefri.data.models.relations.userStores.UsersStores
import com.yefri.data.models.shopList.ShopList
import com.yefri.data.models.shopList.ShopLists
import com.yefri.data.models.store.Store
import com.yefri.data.models.store.Stores
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class User(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<User>(Users)
    var username by Users.username
    var password by Users.password
    var firetoken by Users.firetoken
    var ads by Users.ads
    var receipes by Users.receipes
    fun store(): Store {
        val userStore = transaction {
            UserStores.find { UsersStores.userId eq this@User.id }.firstOrNull()
        }
        val originalStore = transaction {
            Store.find { Stores.userId eq this@User.id }.firstOrNull()
        }
        val store = transaction { userStore?.store }
        return  store ?: originalStore!!
    }
    fun shopList() = transaction {
        ShopList.find { ShopLists.id eq store().id }.firstOrNull()
    }
    fun blockAds() = transaction {
        this@User.ads = false
    }
}