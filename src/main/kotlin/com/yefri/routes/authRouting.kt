package com.yefri.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.yefri.data.database.DataManager
import com.yefri.routes.models.UserForLoginRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.yefri.secret
import com.yefri.audience
import com.yefri.issuer
import com.yefri.data.models.latestversion.AgendaVersion
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.util.*

fun Route.authRouting(dm: DataManager) {
    route("auth") {
        post("/login") {
            val user = call.receive<UserForLoginRequest>()
            val foundedUser = dm.userDao.getUser(user.username, user.password)
            if (foundedUser == null)
                call.respondText("No se ha encontrado el usuario: ${user.username}", status = HttpStatusCode.Unauthorized)
            else {
                val token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim("username", user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 1296000000))
                    .sign(Algorithm.HMAC256(secret))
                call.respond(hashMapOf("token" to token))
            }
        }
        post("/register") {
            val user = call.receive<UserForLoginRequest>()
            val founded = dm.userDao.checkUserNameExists(user.username)
            if (founded)
                call.respondText("El nombre de usuario ya existe")
            else {
                val newUser = dm.userDao.newUser(user.username, user.password)
                dm.storeDao.createStore(newUser)
                dm.shopListDao.createShopList(newUser)
                call.respondText("Registrado con éxito")
            }
        }
        get("/recoveryPassword/{username?}") {
            val username = call.parameters["username"] ?: return@get call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val passwordRecovered = dm.userDao.recoverPassword(username) ?: "usuario incorrecto"
            call.respond(passwordRecovered)
        }
    }
    get("/reactive") {
        call.respond("El servidor está activo")
    }
    get("/latestVersion") {
        var latestVersion = 0
        transaction {
            val version = com.yefri.data.models.latestversion.AgendaVersion.all()
            latestVersion = version.maxBy { it.aversion }.aversion
        }
        call.respond(latestVersion)
    }
    get("/images/{codeBar}") {
        val codeBar = call.parameters["codeBar"] ?: return@get call.respondText(
            "Error de sintaxis -codeBar-", status = HttpStatusCode.BadRequest)
        val image = File("./images/$codeBar")
        if(image.exists())
            call.respondFile(File("./images/$codeBar"))
        else call.respondText("Image not found", status = HttpStatusCode.NotFound)
    }
}