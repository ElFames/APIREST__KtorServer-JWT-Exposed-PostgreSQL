package com.yefri.data.models.product.modelsFromOpenFood

import kotlinx.serialization.Serializable

@Serializable
data class Model3NoAdditives(
    val _id: String?,
    val code: String,
    val allergens: String,
    val allergens_from_ingredients: String,
    val brands: String,
    val ecoscore_grade: String,
    val generic_name: String,
    val image_url: String,
    val ingredients_analysis_tags: List<String>,
    val ingredients_text: String,
    val labels_old: String,
    val nutrient_levels: Map<String, String>,
    val origins: String,
    var quantity: String
) {
    fun toJson() = com.yefri.data.models.product.modelsFromOpenFood.Model1(
        _id,
        code,
        listOf(),
        allergens,
        allergens_from_ingredients,
        brands,
        ecoscore_grade,
        generic_name,
        image_url,
        ingredients_analysis_tags,
        ingredients_text,
        labels_old,
        nutrient_levels,
        listOf(origins),
        quantity
    )
}