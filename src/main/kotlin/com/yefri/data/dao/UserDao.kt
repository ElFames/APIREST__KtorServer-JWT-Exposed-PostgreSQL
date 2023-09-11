package com.yefri.data.dao

import com.yefri.data.models.relations.userStores.UserStores
import com.yefri.data.models.relations.userStores.UsersStores
import com.yefri.data.models.user.User
import com.yefri.data.models.user.Users
import io.ktor.server.http.content.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class UserDao {
    fun getUser(username: String, password: String) = transaction {
        User.find { Users.username eq username and (Users.password eq password) }.firstOrNull()
    }
    fun newUser(newUsername: String, newPassword: String) = transaction {
        User.new {
            username = newUsername
            password = newPassword
        }
    }
    fun blockAds(username: String): Boolean {
        return try {
            val user = getUserByName(username)
            user?.let { user.blockAds() }
            true
        } catch (e: Exception) {
            false
        }
    }
    fun updateFiretoken(username: String, firetoken: String) {
        val user = getUserByName(username)
        transaction {
            user?.let { user.firetoken = firetoken }
        }
    }
    fun changePassword(user: User, password: String) = transaction {
        user.password = password
    }
    fun getUserByName(username: String) = transaction {
        User.find { Users.username eq username }.firstOrNull()
    }

    fun checkUserNameExists(username: String): Boolean {
        val founded = transaction {
            User.find { Users.username eq username }.firstOrNull()
        }
        return founded != null
    }

    fun changeUsername(user: User, newUsername: String) = transaction {
        user.username = newUsername
    }
    fun inviteUser(user: User, userToInvite: String): String {
        val userHost = getUserByName(userToInvite) ?: return "no existe"
        val invitations = transaction {
            com.yefri.data.models.invitations.Invitation.find { com.yefri.data.models.invitations.Invitations.userHost eq userHost.id }.toList()
        }
        return if(invitations.size < 2) {
            transaction {
                com.yefri.data.models.invitations.Invitation.new {
                    this.userSender = user
                    this.userHost = userHost
                }
            }
            "invitado con exito"
        } else "bandeja llena"
    }

    fun getStoreMembers(user: User): MutableList<String> {
        val members = mutableListOf<String>()
        transaction {
            val storeId = user.store().id
            User.all().forEach {
                if (it.store().id == storeId)
                    members.add(it.username)
            }
        }
        return members
    }
    fun getInvitations(user: User) = transaction {
        com.yefri.data.models.invitations.Invitation.find { com.yefri.data.models.invitations.Invitations.userHost eq user.id }.toMutableList()
    }

    fun acceptInvitation(userHost: User, userSenderName: String): Boolean {
        val userSender = getUserByName(userSenderName)
        userSender?.let {
            val invitation = transaction {
                com.yefri.data.models.invitations.Invitation.find { com.yefri.data.models.invitations.Invitations.userHost eq userHost.id and(com.yefri.data.models.invitations.Invitations.userSender eq userSender.id) }.firstOrNull()
            }
            invitation?.let {
                transaction {
                    assignStoreToUser(userHost, userSender)
                    it.delete()
                }
                return true
            }
        }
        return false
    }

    private fun assignStoreToUser(userHost: User, userSender: User) {
        checkUserStore(userHost)
        transaction {
            UserStores.new {
                store = userSender.store()
                user = userHost
            }
        }
    }

    private fun checkUserStore(userHost: User) {
        val secondaryStore = transaction {
            UserStores.find { UsersStores.userId eq userHost.id }.firstOrNull()
        }
        if(secondaryStore != null) {
            transaction {
                secondaryStore.delete()
            }
        }
    }

    fun rejectInvitation(userHost: User, userSenderName: String): Boolean {
        val userSender = getUserByName(userSenderName)
        userSender?.let {
            val invitation = transaction {
                com.yefri.data.models.invitations.Invitation.find { com.yefri.data.models.invitations.Invitations.userHost eq userHost.id and(com.yefri.data.models.invitations.Invitations.userSender eq userSender.id) }.firstOrNull()
            }
            invitation?.delete()
            return true
        }
        return false
    }

    fun recoverPassword(username: String): String? {
        return transaction {
            val user = User.find { Users.username eq username }.firstOrNull()
            user?.password
        }
    }

    fun unlockReceipes(username: String): Boolean {
        var response:Boolean
        try {
            transaction {
                val user = User.find { Users.username eq username }.firstOrNull()
                if (user == null) response = false
                else {
                    user.receipes = true
                    response = true
                }
            }
            response = true
        } catch (e: Exception) {
            response = false
        }
        return response
    }

    fun getReceipeStatus(username: String): Boolean {
        var response = false
        transaction {
            val user = User.find { Users.username eq username }.firstOrNull()
            response = user!!.receipes
        }
        return response
    }

}