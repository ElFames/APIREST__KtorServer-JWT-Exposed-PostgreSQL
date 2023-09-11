package com.yefri.data.models.latestversion

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class LastestVersion (id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<LastestVersion>(LastestVersions)
    var lastestVersion by com.yefri.data.models.latestversion.LastestVersions.lastestVersion
}