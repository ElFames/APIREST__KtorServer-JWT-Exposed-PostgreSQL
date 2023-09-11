package com.yefri.data.models.latestversion

import org.jetbrains.exposed.dao.id.IntIdTable

object AgendaVersions: IntIdTable()  {
    var aversion = integer("aversion")
}