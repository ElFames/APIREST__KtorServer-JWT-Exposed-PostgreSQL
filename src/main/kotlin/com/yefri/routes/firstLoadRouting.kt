package com.yefri.routes

import com.yefri.data.database.DataManager
import com.yefri.data.models.latestversion.LastestVersion
import com.yefri.routes.models.RecetaES
import com.yefri.routes.models.ShopListJsonProduct
import com.yefri.routes.models.StoreJsonProduct
import com.yefri.routes.models.JsonShopList
import com.yefri.routes.models.JsonStore
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.firstLoadRouting(dm: DataManager) {
    route("load") {
        get("/lastestVersion") {
            var lastestVersion = 0
            transaction {
                val version = com.yefri.data.models.latestversion.LastestVersion.all()
                lastestVersion = version.maxBy { it.lastestVersion }.lastestVersion
            }
            call.respond(lastestVersion)
        }
        get("/receipesStatus") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val showReceipes = dm.userDao.getReceipeStatus(username)
            call.respond(showReceipes)
        }
        get("/store") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            val store = user!!.store()
            var jsonStore = JsonStore(store.id.value,"Tu hogar", user.id.value.toString(), mutableListOf())
            val products = mutableListOf<StoreJsonProduct>()
            transaction {
                store.products.forEach { products.add(it.toJson()) }
                jsonStore = JsonStore(store.id.value, store.storeName, store.user.id.value.toString(), products)
            }
            call.respond(jsonStore)
        }
        get("/receipes") {
            val recetas = dm.recetasDao.getAllRecetas()
            val recetasES = mutableListOf<RecetaES>()
            transaction {
                recetas.forEach { recetasES.add(it.toRecetaES()) }
            }
            call.respond(recetasES)
        }
        get("/shopList") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            val shopList = user!!.shopList()
            var jsonShopList = JsonShopList(shopList!!.id.value,"Lista de la Purchase",user.id.value.toString(), mutableListOf())
            val products = mutableListOf<ShopListJsonProduct>()
            transaction {
                shopList.products.forEach { products.add(it.toJson()) }
                jsonShopList = JsonShopList(shopList.id.value, shopList.shopListName, shopList.user.id.value.toString(), products)
            }
            call.respond(jsonShopList)
        }
    }
}