package com.yefri.routes

import com.yefri.data.database.DataManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.userRouting(dm: DataManager) {
    put("/changePassword/{password?}") {
        val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
        val user = dm.userDao.getUserByName(username)
        val password = call.parameters["password"] ?: return@put call.respondText(
            "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
        dm.userDao.changePassword(user!!,password)
        call.respondText("Contraseña cambiada con éxito", status = HttpStatusCode.OK)
    }
    put("/changeUsername/{username?}") {
        val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
        val user = dm.userDao.getUserByName(username)
        val newUsername = call.parameters["username"] ?: return@put call.respondText(
            "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
        dm.userDao.changeUsername(user!!,newUsername)
        call.respondText("Nombre de usuario cambiado con éxito", status = HttpStatusCode.OK)
    }
    post("/inviteUser/{username?}") {
        val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
        val user = dm.userDao.getUserByName(username)
        val userToInvite = call.parameters["username"] ?: return@post call.respondText(
            "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
        val respond = dm.userDao.inviteUser(user!!, userToInvite)
        if (respond == "invitado con exito") call.respondText(respond, status = HttpStatusCode.OK)
        else call.respondText(respond, status = HttpStatusCode.NotFound)
    }
    get("/audience") {
        val principal = call.principal<JWTPrincipal>()
        val username = principal!!.payload.getClaim("username").asString()
        val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
        call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
    }
    get("/getStoreMembers") {
        val principal = call.principal<JWTPrincipal>()
        val username = principal!!.payload.getClaim("username").asString()
        val user = dm.userDao.getUserByName(username)
        val storeMembers = dm.userDao.getStoreMembers(user!!)
        call.respond(storeMembers)
    }
    get("/getAdsStatus") {
        val principal = call.principal<JWTPrincipal>()
        val username = principal!!.payload.getClaim("username").asString()
        val user = dm.userDao.getUserByName(username)
        val ads = transaction {
            user?.ads
        }
        call.respond(ads?:false)
    }
    get("/getInvitations") {
        val principal = call.principal<JWTPrincipal>()
        val username = principal!!.payload.getClaim("username").asString()
        val user = dm.userDao.getUserByName(username)
        val invitations = dm.userDao.getInvitations(user!!)
        val usersSenders = mutableListOf<String>()
        transaction {
            invitations.forEach {
                usersSenders.add(it.userSender.username)
            }
        }
        call.respond(usersSenders)
    }
    put("/acceptInvitation/{invitation?}") {
        val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
        val user = dm.userDao.getUserByName(username)
        val invitation = call.parameters["invitation"] ?: return@put call.respondText(
            "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
        val userSender = invitation.split(":").last().substring(1)
        val checkOk = dm.userDao.acceptInvitation(user!!, userSender)
        if (checkOk) call.respondText("invitacion aceptada", status = HttpStatusCode.OK)
        else call.respondText("error", status = HttpStatusCode.NotFound)
    }
    put("/rejectInvitation/{invitation?}") {
        val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
        val user = dm.userDao.getUserByName(username)
        val invitation = call.parameters["invitation"] ?: return@put call.respondText(
            "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
        val userSender = invitation.split(":").last().substring(1)
        val checkOk = dm.userDao.rejectInvitation(user!!, userSender)
        if (checkOk) call.respondText("invitacion rechazada", status = HttpStatusCode.OK)
        else call.respondText("error", status = HttpStatusCode.NotFound)
    }
    delete("/exitStore") {
        val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
        val user = dm.userDao.getUserByName(username)
        val goneOut = dm.storeDao.exitCurrentStore(user!!)
        call.respond(goneOut)
    }
    put("/uploadFiretoken/{firetoken?}") {
        val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
        val firetoken = call.parameters["firetoken"] ?: return@put call.respondText(
            "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
        dm.userDao.updateFiretoken(username, firetoken)
        call.respond(HttpStatusCode.OK)
    }
    put("/blockAds") {
        val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
        val checkOk = dm.userDao.blockAds(username)
        call.respond(checkOk)
    }
}