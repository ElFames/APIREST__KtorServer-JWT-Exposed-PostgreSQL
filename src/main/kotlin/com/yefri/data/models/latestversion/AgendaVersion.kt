package com.yefri.data.models.latestversion

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AgendaVersion(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<AgendaVersion>(AgendaVersions)

    var aversion by com.yefri.data.models.latestversion.AgendaVersions.aversion
}