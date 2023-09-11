package com.yefri.data.models.recetas

import com.yefri.routes.models.RecetaES
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Receta(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Receta>(Recetas)
    var nombre by Recetas.nombre
    var categoria by Recetas.categoria
    var pais by Recetas.pais
    var instrucciones by Recetas.instrucciones
    var imagenUrl by Recetas.imagenUrl
    var tags by Recetas.tags
    var youtubeUrl by Recetas.youtubeUrl
    var ingredient1 by Recetas.ingredient1
    var ingredient2 by Recetas.ingredient2
    var ingredient3 by Recetas.ingredient3
    var ingredient4 by Recetas.ingredient4
    var ingredient5 by Recetas.ingredient5
    var ingredient6 by Recetas.ingredient6
    var ingredient7 by Recetas.ingredient7
    var ingredient8 by Recetas.ingredient8
    var ingredient9 by Recetas.ingredient9
    var ingredient10 by Recetas.ingredient10
    var ingredient11 by Recetas.ingredient11
    var ingredient12 by Recetas.ingredient12
    var ingredient13 by Recetas.ingredient13
    var ingredient14 by Recetas.ingredient14
    var ingredient15 by Recetas.ingredient15
    var ingredient16 by Recetas.ingredient16
    var ingredient17 by Recetas.ingredient17
    var ingredient18 by Recetas.ingredient18
    var ingredient19 by Recetas.ingredient19
    var ingredient20 by Recetas.ingredient20
    fun toRecetaES() = RecetaES(nombre, categoria, pais, instrucciones, imagenUrl, tags, youtubeUrl, ingredient1,
        ingredient2, ingredient3, ingredient4, ingredient5, ingredient6, ingredient7, ingredient8, ingredient9,
        ingredient10, ingredient11, ingredient12, ingredient13, ingredient14, ingredient15, ingredient16, ingredient17,
        ingredient18, ingredient19, ingredient20)
}