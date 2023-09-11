package com.yefri.core.api

import com.yefri.data.models.product.productResponses.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class OpenFoodApi {
    private val baseUrl = "https://world.openfoodfacts.org/api/v1/product/"
    suspend fun getProduct(codeBar: String): ProductResponse1? {
        try {
            return client.get("$baseUrl$codeBar.json").body<ProductResponse1>()
        } catch (e: Exception) {
            try {
                val response: ProductResponse1yMedio = client.get("$baseUrl$codeBar.json").body()
                println(response)
                return response.toProductResponse()//ProductResponse2yMedia
            } catch (e: Exception) {
                try {
                    val response: ProductResponse2 = client.get("$baseUrl$codeBar.json").body()
                    println(response)
                    return response.toProductResponse()
                } catch (e:Exception){
                    try {
                        val response: ProductResponse2yMedia = client.get("$baseUrl$codeBar.json").body()
                        println(response)
                        return response.toProductResponse()
                    } catch (e: Exception) {
                        try {
                            val response: ProductResponse3 = client.get("$baseUrl$codeBar.json").body()
                            println(response)
                            return response.toProductResponse()
                        } catch (e: Exception) {
                            try {
                                val response: ProductResponse4 = client.get("$baseUrl$codeBar.json").body()
                                println(response)
                                return response.toProductResponse()
                            } catch (e: Exception) {
                                try {
                                    val response: ProductResponse5 = client.get("$baseUrl$codeBar.json").body()
                                    println(response)
                                    return response.toProductResponse()
                                } catch (e: Exception) {
                                    try {
                                        val response: ProductResponse6 = client.get("$baseUrl$codeBar.json").body()
                                        println(response)
                                        return response.toProductResponse()
                                    } catch (e: Exception) {
                                        try {
                                            val response: ProductResponse7 = client.get("$baseUrl$codeBar.json").body()
                                            println(response)
                                            return response.toProductResponse()
                                        } catch (e: Exception) {
                                            try {
                                                val response: ProductResponse8 = client.get("$baseUrl$codeBar.json").body()
                                                println(response)
                                                return response.toProductResponse()
                                            } catch (e: Exception) {
                                                try {
                                                    val response: ProductResponse9 =
                                                        client.get("$baseUrl$codeBar.json").body()
                                                    println(response)
                                                    return response.toProductResponse()
                                                } catch (e: Exception) {
                                                    try {
                                                        val response: ProductResponse10 =
                                                            client.get("$baseUrl$codeBar.json").body()
                                                        println(response)
                                                        return response.toProductResponse()
                                                    } catch (e: Exception) {
                                                        try {
                                                            val response: ProductResponse11 =
                                                                client.get("$baseUrl$codeBar.json").body()
                                                            println(response)
                                                            return response.toProductResponse()
                                                        } catch (e: Exception) {
                                                            try {
                                                                val response: ProductResponse11 =
                                                                    client.get("$baseUrl$codeBar.json").body()
                                                                println(response)
                                                                return response.toProductResponse()
                                                            } catch (e: Exception) {
                                                                try {
                                                                    val response: ProductResponse12 =
                                                                        client.get("$baseUrl$codeBar.json").body()
                                                                    println(response)
                                                                    return response.toProductResponse()
                                                                } catch (e: Exception) {
                                                                    try {
                                                                        val response: ProductResponse13 =
                                                                            client.get("$baseUrl$codeBar.json").body()
                                                                        println(response)
                                                                        return response.toProductResponse()
                                                                    } catch (e: Exception) {
                                                                        try {
                                                                            val response: ProductResponse14 =
                                                                                client.get("$baseUrl$codeBar.json").body()
                                                                            println(response)
                                                                            return response.toProductResponse()
                                                                        } catch (e: Exception) {
                                                                            try {
                                                                                val response: ProductResponse15 =
                                                                                    client.get("$baseUrl$codeBar.json").body()
                                                                                println(response)
                                                                                return response.toProductResponse()
                                                                            } catch (e: Exception) {
                                                                                try {
                                                                                    val response: ProductResponse16 =
                                                                                        client.get("$baseUrl$codeBar.json").body()
                                                                                    println(response)
                                                                                    return response.toProductResponse()
                                                                                } catch (e: Exception) {
                                                                                    return null
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}