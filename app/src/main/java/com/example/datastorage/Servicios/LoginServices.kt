package com.example.datastorage.Servicios

import android.content.Context
import com.example.datastorage.Modelos.User
import android.support.v7.app.AppCompatActivity

class LoginServices(context: Context)
{
    private lateinit var user : User
    private var dbConnection : UserDBServices = UserDBServices(context)
    private var sharedConnection : UserReservedServices = UserReservedServices(context)

    fun verifyUser(user: User) : Boolean
    {
        this.user = user
        var result : Boolean = false

        if(sharedConnection.verifyUser(user))
        {
            result =  sharedConnection.verifyUser(this.user)
        }
        else
            result =  dbConnection.verifyUser(this.user)

        return result
    }

    fun existsUser(user : User) : Boolean {
        return sharedConnection.existsUser(user) || dbConnection.existsUser(user)
    }

    fun saveUser(user : User, context : AppCompatActivity, bdStore : Boolean) {
        if (bdStore) {
            UserDBServices(context).saveUser(user)
        } else {
            UserReservedServices(context).saveUser(user)
        }
    }
}