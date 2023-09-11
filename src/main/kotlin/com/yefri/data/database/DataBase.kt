package com.yefri.data.database

import com.yefri.data.models.invitations.Invitations
import com.yefri.data.models.latestversion.AgendaVersions
import com.yefri.data.models.latestversion.LastestVersions
import com.yefri.data.models.product.Products
import com.yefri.data.models.purchases.Purchases
import com.yefri.data.models.recetas.Recetas
import com.yefri.data.models.relations.shopListProduct.ShopListProducts
import com.yefri.data.models.relations.storeProduct.StoreProducts
import com.yefri.data.models.relations.userStores.UsersStores
import com.yefri.data.models.shopList.ShopLists
import com.yefri.data.models.store.Stores
import com.yefri.data.models.user.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DataBase {
    fun initHerokuDB() {
        val driver = "org.postgresql.Driver"
        val dbUrl = "jdbc:postgresql://ec2-52-215-209-64.eu-west-1.compute.amazonaws.com:5432/d7f1f0p4i0a0p"
        val user = "wmztrsvkkmfhdb"
        val password = ""
        val database = Database.connect(dbUrl, driver, user, password)
        transaction(database) {
            SchemaUtils.create(
                Purchases, Users,
                Products, Stores, ShopLists, StoreProducts, ShopListProducts,
                Invitations, UsersStores, Recetas,
                LastestVersions,
                AgendaVersions
            )
        }
    }
    fun initLocalDB() {
        val driver = "org.postgresql.Driver"
        val dbUrl = "jdbc:postgresql://localhost:5432/postgres"
        val database = Database.connect(dbUrl, driver, user="postgres", password="")
        transaction(database) {
            SchemaUtils.create(Purchases, Users,
                Products, Stores, ShopLists, StoreProducts, ShopListProducts,
                Invitations, UsersStores, Recetas,
                LastestVersions,
                AgendaVersions
            )
        }
    }
}