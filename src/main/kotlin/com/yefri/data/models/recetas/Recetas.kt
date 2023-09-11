package com.yefri.data.models.recetas

import org.jetbrains.exposed.dao.id.IntIdTable

object Recetas: IntIdTable() {
    val nombre = varchar("nombre",2500)
    val categoria = varchar("categoria",2500)
    val pais = varchar("pais",2500)
    val instrucciones = varchar("instrucciones",7000)
    val imagenUrl = varchar("imagenUrl",2500)
    val tags = varchar("tags",2500)
    val youtubeUrl = varchar("youtubeUrl",5000)
    val ingredient1 = varchar("ingredient1",2500)
    val ingredient2 = varchar("ingredient2",2500)
    val ingredient3 = varchar("ingredient3",2500)
    val ingredient4 = varchar("ingredient4",2500)
    val ingredient5 = varchar("ingredient5",2500)
    val ingredient6 = varchar("ingredient6",2500)
    val ingredient7 = varchar("ingredient7",2500)
    val ingredient8 = varchar("ingredient8",2500)
    val ingredient9 = varchar("ingredient9",2500)
    val ingredient10 = varchar("ingredient10",2500)
    val ingredient11 = varchar("ingredient11",2500)
    val ingredient12 = varchar("ingredient12",2500)
    val ingredient13 = varchar("ingredient13",2500)
    val ingredient14 = varchar("ingredient14",2500)
    val ingredient15 = varchar("ingredient15",2500)
    val ingredient16 = varchar("ingredient16",2500)
    val ingredient17 = varchar("ingredient17",2500)
    val ingredient18 = varchar("ingredient18",2500)
    val ingredient19 = varchar("ingredient19",2500)
    val ingredient20 = varchar("ingredient20",2500)
}