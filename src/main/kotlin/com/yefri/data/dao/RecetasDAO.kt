package com.yefri.data.dao

import com.yefri.data.models.recetas.Receta
import com.yefri.data.models.user.User
import com.yefri.routes.models.RecetaES
import org.jetbrains.exposed.sql.transactions.transaction

class RecetasDAO {
    fun insertNewReceta(receta: RecetaES) {
        transaction {
            Receta.new {
                nombre = receta.nombre
                categoria = receta.categoria
                pais = receta.pais
                instrucciones = receta.instrucciones
                imagenUrl = receta.imagenUrl
                tags = receta.tags
                youtubeUrl = receta.youtubeUrl
                ingredient1 = receta.ingredient1
                ingredient2 = receta.ingredient2
                ingredient3 = receta.ingredient3
                ingredient4 = receta.ingredient4
                ingredient5 = receta.ingredient5
                ingredient6 = receta.ingredient6
                ingredient7 = receta.ingredient7
                ingredient8 = receta.ingredient8
                ingredient9 = receta.ingredient9
                ingredient10 = receta.ingredient10
                ingredient11 = receta.ingredient11
                ingredient12 = receta.ingredient12
                ingredient13 = receta.ingredient13
                ingredient14 = receta.ingredient14
                ingredient15 = receta.ingredient15
                ingredient16 = receta.ingredient16
                ingredient17 = receta.ingredient17
                ingredient18 = receta.ingredient18
                ingredient19 = receta.ingredient19
                ingredient20 = receta.ingredient20
            }
        }
    }

    fun getAllRecetas() = transaction { Receta.all() }
    fun getAvailableReceipes(user: User): MutableList<Receta> {
        val recetasDisponibles = mutableListOf<Receta>()
        var recetaOkay: Boolean
        val store = user.store()
        val availableIngredients = mutableListOf<String>()
        transaction { store.products.forEach { availableIngredients.add(it.product.productName?:"") } }
        val recetas = transaction { Receta.all().toMutableList() }
        println("recetas recuperadas: ${recetas.size}")
        transaction {
            var ingredientesNecesarios: MutableList<String>
            recetas.forEach { receta ->
                println("\nanalizando ${receta.nombre}")
                ingredientesNecesarios = mutableListOf(receta.ingredient1.split(" ").last(),receta.ingredient2.split(" ").last(),receta.ingredient3.split(" ").last(),receta.ingredient4.split(" ").last(),receta.ingredient5.split(" ").last(),receta.ingredient6.split(" ").last(),receta.ingredient7.split(" ").last(),receta.ingredient8.split(" ").last(),receta.ingredient9.split(" ").last(),receta.ingredient10.split(" ").last(),receta.ingredient11.split(" ").last(),receta.ingredient12.split(" ").last(),receta.ingredient13.split(" ").last(),receta.ingredient14.split(" ").last(),receta.ingredient15.split(" ").last(),receta.ingredient16.split(" ").last(),receta.ingredient17.split(" ").last(),receta.ingredient18.split(" ").last(),receta.ingredient19.split(" ").last(),receta.ingredient20.split(" ").last())
                ingredientesNecesarios.forEach { println(it) }
                recetaOkay = checkIngredients(availableIngredients, ingredientesNecesarios)
                ingredientesNecesarios = mutableListOf()
                println("lista de ingredientes restablecida a vacia. tamaño: ${ingredientesNecesarios.size}")
                if (recetaOkay) {
                    println("receta disponible")
                    recetasDisponibles.add(receta)
                    recetaOkay = false
                } else println("receta NO disponible")
            }
        }
        if (recetasDisponibles.isEmpty()) {
            println("No está disponible ninguna receta con esos ingredientes")
        } else {
            println("Estas son las recetas disponibles:\n")
            recetasDisponibles.forEach {
                println("${it.nombre}\n")
            }
        }
        return recetasDisponibles
    }

private fun checkIngredients(availableIngredients: MutableList<String>, ingredientesNecesarios: MutableList<String>): Boolean {
    ingredientesNecesarios.forEach { ingredient ->
        return if (checkIngredient(ingredient)) {
            println("ingrediente NO es null")
            ingredientExists(ingredient, availableIngredients)
        } else {
            println("no hay mas ingredientes")
            false
        }
    }
    return true
}
private fun ingredientExists(ingredient: String, availableIngredients: MutableList<String>): Boolean {
    if (ingredient.lowercase().contains(Regex(".agua.*"))) return true
    if (ingredient.lowercase().contains(Regex(".sal.*"))) return true
    availableIngredients.forEach {
        println("ingrediente disponible: $it \ningrediente que se necesita: $ingredient")
        if (ingredient.lowercase().contains(Regex(".${it.lowercase()}.*"))) {
            println("ingrediente disponible")
            return true
        }
    }
    println("no se dispone del ingrediente")
    return false
}
private fun checkIngredient(ingredient: String?): Boolean {
    println("chequeando: $ingredient")
    return ingredient != "" && ingredient != " " && ingredient != null && ingredient != "null"
}

}