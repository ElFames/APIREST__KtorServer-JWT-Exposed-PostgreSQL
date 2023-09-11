package com.yefri.data.models.user

import org.jetbrains.exposed.dao.id.IntIdTable

object Users: IntIdTable()  {
    var username = varchar("username", 500).uniqueIndex()
    var password = varchar("password", 500)
    var firetoken = varchar("firetoken", 3000).nullable()
    var ads = bool("ads").default(true)
    var receipes = bool("receipes").default(false)
}
