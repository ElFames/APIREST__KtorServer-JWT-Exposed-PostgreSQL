package com.yefri.data.models.product.modelsFromOpenFood

import com.yefri.routes.models.ProductJson
import kotlinx.serialization.Serializable

@Serializable
data class Model1(
    val _id: String?,
    val code: String,
    val additives_tags: List<String>,
    val allergens: String,
    val allergens_from_ingredients: String,
    val brands: String,
    val ecoscore_grade: String,
    val product_name: String,
    val image_url: String,
    val ingredients_analysis_tags: List<String>,
    val ingredients_text: String,
    val labels_old: String,
    val nutrient_levels: Map<String, String>,
    val countries_tags: List<String>,
    var quantity: String) {
    fun toProductJson() = ProductJson(
        null,
        code,
        getProductName(),
        getBrand(),
        0.0,
        setPoints(),
        getSugar(),
        getFat(),
        getSaturatedFat(),
        getSalt(),
        analicePalmOil(),
        analiceAdditives(),
        setAllergens(),
        analiceVegans(),
        analiceVegetarian(),
        analiceGluten(),
        setEcoScoreGrade(),
        setOrigin(),
        quantity,
        ingredients_text,
        image_url,
        "",
        "",
    )

    private fun analiceAdditives(): Int {
        if (additives_tags.isEmpty()) return 0
        else {
            val highDangerAdditives = mutableListOf("e950","e951","e150","e338","e150d")
            val mediumDangerAdditives = mutableListOf("e202","e959","e200","e476","e422","e420i")
            additives_tags.forEach { additive ->
                highDangerAdditives.forEach { highDangerAdditive ->
                    if (additive.contains(Regex(".*$highDangerAdditive.*")))
                        return 1
                }
                mediumDangerAdditives.forEach { mediumDangerAdditives ->
                    if (additive.contains(Regex(".*$mediumDangerAdditives.*")))
                        return 2
                }
            }
            return 3
        }
    }

    private fun setPoints(): Int {
        var pointsValue = 0
        val gluten = analiceGluten()
        gluten?.let { if (gluten) pointsValue += 3 }
        val vegans = analiceVegans()
        vegans?.let { if (vegans) pointsValue += 3 }
        val vegetarians = analiceVegetarian()
        vegetarians?.let { if (vegetarians) pointsValue += 3 }
        when(getFat()) {
            3 -> pointsValue += 24
            2 -> pointsValue += 16
            1 -> pointsValue -= 10
        }
        when(getSalt()) {
            3 -> pointsValue += 22
            2 -> pointsValue += 18
            1 -> pointsValue -= 11
        }
        when(getSaturatedFat()) {
            3 -> pointsValue += 26
            2 -> pointsValue += 15
            1 -> pointsValue -= 15
        }
        when(getSugar()) {
            3 -> pointsValue += 28
            2 -> pointsValue += 13
            1 -> pointsValue -= 17
        }
        when(analicePalmOil()) {
            2 -> pointsValue -= 7
            1 -> pointsValue -= 15
        }
        when(analiceAdditives()) {
            2 -> pointsValue -= 15
            1 -> pointsValue -= 30
        }
        if (pointsValue < 0) pointsValue = 0
        if (pointsValue > 100) pointsValue = 100
        return pointsValue
    }
    private fun getProductName(): String {
        return if (product_name.isEmpty()) "Nombre Desconocido"
        else {
            var name = product_name.split(".").first()
            if (name.length >= 39) name = name.substring(0,39)
            name
        }
    }
    private fun setEcoScoreGrade(): String {
        val labelEcoScores = mutableListOf("unknown","not-applicable")
        var ecoScore = ecoscore_grade
        ecoScore = ecoScore.replace("not-applicable","")
        ecoScore = ecoScore.replace("unknown","")
        if (labels_old.contains("green-dot")) {
            ecoScore = if (ecoScore.isEmpty()) "GreenDot" else "${ecoScore.uppercase()}, GreenDot"
        }
        if (labels_old.lowercase().contains("punto verde")) {
            ecoScore = if (ecoScore.isEmpty()) "PuntoVerde" else "${ecoScore.uppercase()}, PuntoVerde"
        }
        if (ecoScore.isEmpty() || labelEcoScores.contains(ecoScore)) ecoScore = "Desconocida"
        return ecoScore
    }
    private fun getBrand(): String {
        return if (brands.isEmpty()) "Marca Desconocida"
        else {
            var name = brands.split(",").first()
            if (name.length >= 26) name = name.substring(0,26)
            name
        }
    }
    private fun analiceGluten(): Boolean? {
        val glutenLabels = mutableListOf("gluten-free","No gluten") // habra alguno mas
        if (labels_old != "") {
            glutenLabels.forEach {
                if (labels_old.contains(it)) return true
            }
        }
        return null
    }
    private fun setOrigin(): String {
        var origin = ""
        var countries =
            if (countries_tags.size >= 3) countries_tags.subList(0,2)
            else countries_tags
        countries.forEach {
            origin += "${it.replace("en:","")} "
        }
        return origin.ifEmpty { "Desconocido" }
    }
    private fun analicePalmOil(): Int {
        if (ingredients_analysis_tags.isNotEmpty()) {
            ingredients_analysis_tags.forEach {
                if (it.contains(Regex(".*contain-palm-oil.*"))) return 1
                else if(it.contains(Regex(".*may-contain-palm-oil.*"))) return 2
                else if(it.contains(Regex(".*palm-oil-free.*"))) return 3
            }
        }
        return 0
    }
    private fun analiceVegans(): Boolean? {
        if (ingredients_analysis_tags.isNotEmpty()) {
            ingredients_analysis_tags.forEach {
                if (it.contains(Regex(".*non-vegan.*"))) return false
                else if(it.contains(Regex(".*vegano.*"))) return true
                else if(it.contains(Regex(".*vegan.*"))) return true
            }
        }
        return null
    }
    private fun analiceVegetarian(): Boolean? {
        if (ingredients_analysis_tags.isNotEmpty()) {
            ingredients_analysis_tags.forEach {
                if (it.contains(Regex(".*non-vegetarian.*"))) return false
                else if(it.contains(Regex(".*vegetariano.*"))) return true
                else if(it.contains(Regex(".*vegetarian.*"))) return true
            }
        }
        return null
    }
    private fun setAllergens(): String {
        val alergias =
            allergens.replace("en:","").ifEmpty {
                allergens_from_ingredients.replace("en:","").ifEmpty {
                    "Se Desconoce"
                }
            }
        var alergiasEsp = ""
        return if (alergias != "Se Desconoce") {
            mutableListOf(Pair("celery","apio"),Pair("eggs","huevo"),Pair("milk","leche"),Pair("soybeans","soja"),Pair("sulphites","sulfitos")).forEach {
                if (alergias.lowercase().contains(Regex(".*${it.first}.*")) ||
                    alergias.lowercase().contains(Regex(".*${it.second}.*")))
                    alergiasEsp += "${it.second} "
            }
            alergiasEsp
        } else alergias
    }
    private fun getSugar(): Int {
        nutrient_levels.forEach {
            if (it.key == "sugars") {
                return when(it.value) {
                    "high" -> 1
                    "medium" -> 2
                    "moderate" -> 2
                    "low" -> 3
                    else -> 0
                }
            }
        }
        return 0
    }
    private fun getSalt(): Int {
        nutrient_levels.forEach {
            if (it.key == "salt") {
                return when(it.value) {
                    "high" -> 1
                    "medium" -> 2
                    "moderate" -> 2
                    "low" -> 3
                    else -> 0
                }
            }
        }
        return 0
    }
    private fun getFat(): Int {
        nutrient_levels.forEach {
            if (it.key == "fat") {
                return when(it.value) {
                    "high" -> 1
                    "medium" -> 2
                    "moderate" -> 2
                    "low" -> 3
                    else -> 0
                }
            }
        }
        return 0
    }
    private fun getSaturatedFat(): Int {
        nutrient_levels.forEach {
            if (it.key == "saturated-fat") {
                return when(it.value) {
                    "high" -> 1
                    "medium" -> 2
                    "moderate" -> 2
                    "low" -> 3
                    else -> 0
                }
            }
        }
        return 0
    }

}