package com.yefri.data.models.relations.userStores

import com.yefri.data.models.store.Store
import com.yefri.data.models.user.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserStores(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserStores>(UsersStores)
    var store by Store referencedOn UsersStores.storeId
    var user by User referencedOn UsersStores.userId
}