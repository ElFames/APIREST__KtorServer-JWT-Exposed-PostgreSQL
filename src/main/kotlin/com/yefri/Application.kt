package com.yefri

import io.ktor.server.application.*
import java.io.FileInputStream
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.yefri.data.database.DataBase
import com.yefri.plugins.configureRouting
import com.yefri.plugins.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*

const val secret = "secret"
const val issuer = "https://yefriapp.herokuapp.com/"
const val audience = "https://yefriapp.herokuapp.com/audience/"
const val myRealm = ""

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toInt() ?: 8080, module = Application::module).start(wait = true)
}
fun Application.module() {
    //DataBase.initLocalDB()
    DataBase.initHerokuDB()
    configureRouting()
    configureSerialization()
}