package com.yefri.routes

import com.yefri.data.database.DataManager
import com.yefri.routes.models.JsonPurchase
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*

fun Route.comprasRouting(dm: DataManager) {
    route("compras") {
        get("/allPurchases") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            val allPurchases = dm.purchasesDao.getAllPurchases(user!!)
            call.respond(allPurchases)
        }
        get("/userPurchases") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            val userPurchases = dm.purchasesDao.getUserPurchases(user!!)
            call.respond(userPurchases)
        }
        post("/newPurchase") {
            val purchase = call.receive<JsonPurchase>()
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            try {
                dm.purchasesDao.addNewPurchase(purchase, user!!)
                call.respond(true)
            } catch (e: Exception) {
                call.respond(false)
            }
        }
        put("/newPurchasesFromList") {
            val purchases = call.receive<MutableList<JsonPurchase>>()
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            try {
                purchases.forEach { dm.purchasesDao.addNewPurchase(it, user!!) }
                call.respond(true)
            } catch (e: Exception) {
                call.respond(false)
            }
        }
    }
}