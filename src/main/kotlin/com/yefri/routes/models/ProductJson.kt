package com.yefri.routes.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductJson(
    var id: Int?,
    var code: String,
    var productName: String,
    var brands: String,
    var price: Double?,
    var points: Int,
    var sugar: Int,
    var fat: Int,
    var saturatedFat: Int,
    var salt: Int,
    var palmOil: Int,
    var additives: Int,
    var allergens: String,
    var vegan: Boolean?,
    var vegetarian: Boolean?,
    var celiaco: Boolean?,
    var ecoscoreGrade: String,
    var origins: String,
    var quantity: String,
    var ingredientsText: String,
    var imageUrl: String?,
    var imageNutrimentsUrl: String?,
    var imageIngredientsUrl: String?)