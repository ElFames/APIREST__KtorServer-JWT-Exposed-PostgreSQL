package com.yefri.routes

import com.yefri.data.database.DataManager
import com.yefri.routes.models.RecetaES
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.recetasRouting(dm: DataManager) {
    route("receta") {
        put ("unlockReceipes") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val changed = dm.userDao.unlockReceipes(username)
            call.respond(changed)
        }
        get("/availableReceipes") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            val recetas = dm.recetasDao.getAvailableReceipes(user!!)
            val recetasES = mutableListOf<RecetaES>()
            transaction {
                recetas.forEach { recetasES.add(it.toRecetaES()) }
            }
            call.respond(recetasES)
        }
        post("newReceipe") {
            val receta = call.receive<RecetaES>()
            dm.recetasDao.insertNewReceta(receta)
            call.respond(true)
        }
    }
}