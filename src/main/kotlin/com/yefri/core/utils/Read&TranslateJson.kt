package com.yefri.core.utils

import com.yefri.data.models.recetas.Recetas
import com.yefri.routes.models.RecetaES
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText


val homePath: String = System.getProperty("user.home")
var recetasTraducidasPath = Path("$homePath/Desktop/recetasTraducidas.json")
var recetasPath = Path("$homePath/Desktop/recetasEnIngles.json")
var recetasTraducidas = mutableListOf<RecetaES>()
var recetas: Recetas? = null

fun main() {
    if (recetasPath.exists()) {
        loadRecetas()
        //recetas?.let { recetasTranslater(it) }
    } else println("No existe o no encuentra el archivo")
}
fun saveRecetas() {
    val json = Json.encodeToString(recetasTraducidas)
    recetasTraducidasPath.writeText(json)
}

private val json = Json { ignoreUnknownKeys = true }

fun loadRecetas() {
    val jsonString = recetasPath.readText()
    recetas = json.decodeFromString(jsonString)
}
/*fun recetasTranslater(recetas: Receta) {
    try {
        recetas.meals.forEachIndexed {i, it ->
            println("\n*********************************************\n*Seleccionando receta $i")
            val receta = RecetaES(
                translateText(it.strMeal),
                it.strCategory?:"",
                translateText(it.strArea?:""),
                translateText(it.strInstructions?:""),
                it.strMealThumb?:"",
                it.strTags?:"",
                it.strYoutube?:"",
                "${it.strMeasure1} ${translateText(it.strIngredient1?:"")}",
                "${it.strMeasure2} ${translateText(it.strIngredient2?:"")}",
                "${it.strMeasure3} ${translateText(it.strIngredient3?:"")}",
                "${it.strMeasure4} ${translateText(it.strIngredient4?:"")}",
                "${it.strMeasure5} ${translateText(it.strIngredient5?:"")}"
            )
            recetasTraducidas.add(receta)
            saveRecetas()
        }
    } catch (e: Exception) {
        saveRecetas()
        println("${e.printStackTrace()}***")
    }
}*/
fun translateText(text: String): String {
    val authKey = "96605867-40a3-f9f8-adaa-6f6498f86eea:fx"
    val endpoint = "https://api-free.deepl.com/v2/translate"
    val targetLang = "ES"

    println("**Codificando texto -> **$text**")
    val encodedText = URLEncoder.encode(text, "UTF-8")

    println("***Creando conexión con el traductor")
    val url = URL(endpoint)
    val connection = url.openConnection() as HttpURLConnection
    connection.setRequestProperty("Authorization", "DeepL-Auth-Key $authKey")
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
    connection.requestMethod = "POST"
    connection.doOutput = true

    println("****Haciendo peticion de traduccion")
    val requestBody = "text=$encodedText&target_lang=$targetLang"
    connection.outputStream.use { outputStream ->
        outputStream.write(requestBody.toByteArray())
        outputStream.flush()
    }

    println("*****Checkeando respuesta de la peticion")
    val responseCode = connection.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        println("******Respuesta correcta... obteniendo respuesta")
        BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
            val response = StringBuffer()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }/*
            val traslations = json.decodeFromString<Traslations>(response.toString())
            val translatedText = traslations.translations.first().text
            println("*******Texto traducido -> $translatedText")
            return translatedText*/
            return ""
        }
    } else {
        throw Exception("Error al realizar la solicitud HTTP. Código de respuesta: $responseCode")
    }
}