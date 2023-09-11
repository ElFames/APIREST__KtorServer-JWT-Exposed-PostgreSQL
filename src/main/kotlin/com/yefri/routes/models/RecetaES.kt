package com.yefri.routes.models

import kotlinx.serialization.Serializable

@Serializable
data class RecetaES (
    val nombre: String,
    val categoria: String,
    val pais: String,
    val instrucciones: String,
    val imagenUrl: String,
    val tags: String,
    val youtubeUrl: String,
    val ingredient1: String,
    val ingredient2: String,
    val ingredient3: String,
    val ingredient4: String,
    val ingredient5: String,
    val ingredient6: String,
    val ingredient7: String,
    val ingredient8: String,
    val ingredient9: String,
    val ingredient10: String,
    val ingredient11: String,
    val ingredient12: String,
    val ingredient13: String,
    val ingredient14: String,
    val ingredient15: String,
    val ingredient16: String,
    val ingredient17: String,
    val ingredient18: String,
    val ingredient19: String,
    val ingredient20: String
)
