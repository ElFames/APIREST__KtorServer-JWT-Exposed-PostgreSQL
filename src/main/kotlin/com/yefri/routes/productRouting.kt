package com.yefri.routes

import com.yefri.data.database.DataManager
import com.yefri.routes.models.ProductJson
import com.yefri.core.utils.saveImage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRouting(dm: DataManager) {
    route("product") {
        get("/manage/{codeBar?}") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            val codeBar = call.parameters["codeBar"] ?: return@get call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val product = dm.scanProduct(codeBar,user!!) ?: return@get call.respondText(
                "No se ha encontrado el producto con el codigo de barras: $codeBar", status = HttpStatusCode.NotFound)
            call.respond(product)
        }
        put("/moveProductsSelected") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            val codebarProducts = call.receive<MutableList<String>>()
            val moved = dm.moveSelectedProductsToStore(codebarProducts, user!!)
            if (moved) call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.BadRequest)
        }
        get("/view/{codeBar?}") {
            val codeBar = call.parameters["codeBar"] ?: return@get call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val product = dm.getProduct(codeBar) ?: return@get call.respondText(
                "No se ha encontrado el producto con el codigo de barras: $codeBar", status = HttpStatusCode.NotFound)
            call.respond(product)
        }
        get("/{codeBar?}/getPoints") {
            val codeBar = call.parameters["codeBar"] ?: return@get call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val product = dm.productDao.getUpdatedPoints(codeBar)
            call.respond(product)
        }
        put("/toStore/{codeBar?}") {
            val codeBar = call.parameters["codeBar"] ?: return@put call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            dm.moveProdToStoreFromShopList(user!!, codeBar)
            call.respond(HttpStatusCode.OK)
        }
        put("/update/{codeBar?}/{quantity?}/{site?}") {
            val codeBar = call.parameters["codeBar"] ?: return@put call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val quantity = call.parameters["quantity"] ?: return@put call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val site = call.parameters["site"] ?: return@put call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            if (site == "store")
                dm.storeDao.updateUnds(user!!, codeBar, quantity.toInt())
            else dm.shopListDao.updateUnds(user!!, codeBar, quantity.toInt())
            call.respond(HttpStatusCode.OK)
        }
        put("/updatePrice/{codeBar?}/{price?}") {
            val codeBar = call.parameters["codeBar"] ?: return@put call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val priceParam = call.parameters["price"]
            val price: Double = try {
                priceParam?.toDoubleOrNull() ?: throw IllegalArgumentException()
            } catch (e: IllegalArgumentException) {
                return@put call.respondText(
                    "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            }
            try {
                dm.productDao.updatePrice(codeBar, price)
                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "El valor 'price' debe ser un número decimal (double)")
            }
        }
        put("/toShopList/{codeBar?}") {
            println("hola")
            val codeBar = call.parameters["codeBar"] ?: return@put call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            dm.moveProdToShopListFromStore(user!!, codeBar)
            call.respond(HttpStatusCode.OK)
        }
        delete("store/delete/{codeBar?}") {
            val codeBar = call.parameters["codeBar"] ?: return@delete call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            dm.storeDao.deleteProduct(codeBar,user!!)
            call.respond(HttpStatusCode.OK)
        }
        delete("shopList/delete/{codeBar?}") {
            val codeBar = call.parameters["codeBar"] ?: return@delete call.respondText(
                "Error de sintaxis o faltan parametros", status = HttpStatusCode.BadRequest)
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            dm.shopListDao.deleteProduct(codeBar,user!!)
            call.respond(HttpStatusCode.OK)
        }
        put("/updateProduct") {
            val product = call.receive<ProductJson>()
            dm.productDao.updateProduct(product)
            call.respond(HttpStatusCode.OK)
        }
        put("/deleteAllProductsFromStore") {
            val deleted = dm.storeDao.deleteAllProducts()
            call.respond(deleted)
        }
        put("/deleteAllProductsFromShopList") {
            val deleted = dm.shopListDao.deleteAllProducts()
            call.respond(deleted)
        }
        post("/newProduct") {
            val newProduct = call.receive<ProductJson>()
            val productAdded = dm.productDao.newProduct(newProduct)
            if (productAdded == null) call.respond("El codigo de barras ya existe")
            else call.respond("Producto añadido con éxito")
        }
        put("/copyShopListToStore") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            dm.shopListToStore(user!!)
            call.respond(HttpStatusCode.OK)
        }
        put("/copyStoreToShopList") {
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            dm.storeToShopList(user!!)
            call.respond(HttpStatusCode.OK)
        }

        post("saveInShopList/{codeBar?}/{unds?}") {
            val codeBar = call.parameters["codeBar"] ?: return@post call.respondText(
                "Error de sintaxis -codeBar-", status = HttpStatusCode.BadRequest)
            val unds = call.parameters["unds"] ?: return@post call.respondText(
                "Error de sintaxis -unds-", status = HttpStatusCode.BadRequest)
            val username = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
            val user = dm.userDao.getUserByName(username)
            dm.saveInShopList(user!!, codeBar, unds)
            call.respond(HttpStatusCode.OK)
        }
        post("/uploadImage/{codeBar?}") {
            val codeBar = call.parameters["codeBar"] ?: return@post call.respondText(
                "Error de sintaxis -codeBar-", status = HttpStatusCode.BadRequest)
            try {
                saveImage(call.receiveMultipart())
                val check = dm.productDao.updateImage("https://yefriapp.herokuapp.com/images/$codeBar", codeBar)
                if (check) call.respond("exito")
                else call.respond("error al guardar")
            } catch (e: Exception) {
                call.respond("error de formato")
            }
        }
    }
}