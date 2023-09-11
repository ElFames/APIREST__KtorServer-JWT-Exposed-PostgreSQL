package com.yefri.data.database

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.yefri.data.dao.*
import com.yefri.routes.models.ProductJson
import com.yefri.core.api.OpenFoodApi
import com.yefri.data.models.relations.shopListProduct.ShopListProduct
import com.yefri.data.models.relations.storeProduct.StoreProduct
import com.yefri.data.models.user.User
import org.jetbrains.exposed.sql.transactions.transaction

object DataManager {
    val storeDao = StoreDao()
    val shopListDao = ShopListDao()
    val userDao = UserDao()
    val productDao = ProductDao()
    val recetasDao = RecetasDAO()
    val purchasesDao = PurchasesDao()
    private val api = OpenFoodApi()

    suspend fun scanProduct(codeBar: String, user: User): ProductJson? {
        return getProduct(codeBar, user, manage = true)
    }
    private fun manageProduct(user: User, productInfo: com.yefri.data.models.product.Product) {
        val store = user.store()
        val shopList = user.shopList()
        val storeProduct = storeDao.getProductByCode(productInfo.code, store)
        val shopListProduct = shopListDao.getProductByCode(productInfo.code, shopList!!)
        transaction {
            if (storeProduct != null) {
                if (storeProduct.quantity == 1) {
                    if (shopListProduct != null) shopListDao.updateUnds(user, productInfo.code, 1)
                    else shopListDao.cloneProduct(storeProduct, shopList)
                    storeDao.deleteProduct(productInfo.code, user)
                    sendNotification(user, storeProduct)
                } else {
                    storeDao.updateUnds(user, productInfo.code, -1)
                    if(shopListProduct == null)
                        shopListDao.addProductByInfo(productInfo, shopList, (1).toString())
                    else shopListDao.updateUnds(user, productInfo.code, 1)
                }
            } else if(shopListProduct == null) {
                storeDao.addProduct(productInfo,store)
            } else {
                return@transaction
            }
        }
    }

    private fun sendNotification(user: User, storeProduct: StoreProduct) {
        val tokens = mutableListOf<String>()
        val usersToSendMessage = userDao.getStoreMembers(user)
        usersToSendMessage.forEach {
            tokens.add(it)
        }
        if (tokens.isNotEmpty()) {
            val message = MulticastMessage.builder()
                .putData("title", "Se ha agotado un producto")
                .putData("body", "¡${user.username} ha agotado ${storeProduct.product.productName}!")
                .addAllTokens(tokens)
                .build()
            FirebaseMessaging.getInstance().sendMulticast(message)
        }
    }

    fun shopListToStore(user: User) {
        transaction {
            val shopList = user.shopList()
            val store = user.store()
            shopList!!.products.forEach {
                if(storeDao.getProductByCode(it.product.code,store) == null) {
                    StoreProduct.new {
                        this.store = store
                        this.product = it.product
                        this.quantity = it.quantity
                    }
                } else {
                    storeDao.updateUnds(user,it.product.code,it.quantity)
                }
            }
            shopList.products.forEach { shopListDao.deleteProduct(it.product.code, user) }
        }
    }

    fun storeToShopList(user: User) {
        val store = user.store()
        val shopList = user.shopList()
        transaction {
            store.products.forEach {
                if (shopListDao.getProductByCode(it.product.code, shopList!!) == null) {
                    ShopListProduct.new {
                        this.shopList = shopList
                        this.product = it.product
                        this.quantity = it.quantity
                    }
                } else {
                    shopListDao.updateUnds(user,it.product.code,it.quantity)
                }
            }
            store.products.forEach { storeDao.deleteProduct(it.product.code, user) }
        }
    }
    suspend fun getProduct(codeBar: String, user: User? = null, manage: Boolean = false): ProductJson? {
        val productFromBD = productDao.getProduct(codeBar)
        return if (productFromBD == null) {
            val productFromApi = api.getProduct(codeBar)?.product
            return if (productFromApi == null) null
            else {
                val product = productDao.newProduct(productFromApi.toProductJson())
                product?.let {
                    if (manage) manageProduct(user!!, product)
                    product.toJson()
                }
            }
        } else {
            if (manage) manageProduct(user!!, productFromBD)
            productFromBD.toJson()
        }
    }

    fun moveProdToStoreFromShopList(user: User, codeBar: String) {
        val store = user.store()
        val shopList = user.shopList()
        val shopListProduct = shopListDao.getProductByCode(codeBar, shopList!!)
        val storeProduct = storeDao.getProductByCode(codeBar, store)
        transaction {
            if (storeProduct != null) {
                storeDao.updateUnds(user, codeBar, storeProduct.quantity)
                shopListDao.deleteProduct(codeBar, user)
            } else {
                storeDao.cloneProduct(shopListProduct, store)
                shopListDao.deleteProduct(codeBar, user)
            }
        }
    }

    fun moveProdToShopListFromStore(user: User, codeBar: String) {
        val shopList = user.shopList()
        val store = user.store()
        val storeProduct = storeDao.getProductByCode(codeBar, store)
        val shopListProduct = shopListDao.getProductByCode(codeBar, shopList!!)
        transaction {
            if (shopListProduct != null) {
                shopListDao.updateUnds(user, codeBar, shopListProduct.quantity)
                storeDao.deleteProduct(codeBar, user)
            } else {
                shopListDao.cloneProduct(storeProduct, shopList)
                storeDao.deleteProduct(codeBar, user)
            }
        }
    }

    fun saveInShopList(user: User, codeBar: String, unds: String) {
        val product = productDao.getProduct(codeBar)
        if (product != null) {
            val shopList = user.shopList()
            shopListDao.addProductByInfo(product, shopList!!, unds)
        }
    }

    fun moveSelectedProductsToStore(products: MutableList<String>, user: User): Boolean {
        return try {
            products.forEach {
                moveProdToStoreFromShopList(user, it)
            }
            true
        } catch (e: Exception) {
            false
        }

    }
}