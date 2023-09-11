package com.yefri.data.models.latestversion

import org.jetbrains.exposed.dao.id.IntIdTable

object LastestVersions: IntIdTable() {
    val lastestVersion = integer("lastest_version")
}