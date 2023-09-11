package com.yefri.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.yefri.data.database.DataManager
import com.yefri.myRealm
import com.yefri.secret
import com.yefri.audience
import com.yefri.issuer
import com.yefri.routes.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    val dm = DataManager

    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT
                .require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString().isNullOrEmpty())
                    null
                else JWTPrincipal(credential.payload)
            }
            challenge {_,_ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
    routing {
        authenticate("auth-jwt") {
            firstLoadRouting(dm)
            productRouting(dm)
            userRouting(dm)
            recetasRouting(dm)
            comprasRouting(dm)
        }
        authRouting(dm)
    }
}
