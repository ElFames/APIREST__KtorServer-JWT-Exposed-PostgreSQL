package com.yefri.data.models.invitations

import com.yefri.data.models.user.User
import com.yefri.routes.models.JsonInvitation
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Invitation(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Invitation>(Invitations)

    var userSender by User referencedOn Invitations.userSender
    var userHost by User referencedOn Invitations.userHost

    fun toJsonInvitation() = JsonInvitation(
        id.value,
        userSender.username,
        userHost.username
    )
}