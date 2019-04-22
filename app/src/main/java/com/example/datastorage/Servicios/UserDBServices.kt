package com.example.datastorage.Servicios

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import com.example.datastorage.Modelos.User

class UserDBServices(context: Context) : SQLiteOpenHelper(context, "UserDBService", null, 1), IUserServices
{
    fun restoreDB(db : SQLiteDatabase?) {
        val sqlDestroy : String = "DROP TABLE users";
        db?.execSQL(sqlDestroy)

        val sql : String = "CREATE TABLE users(idUser int primarykey," +
                " name text," +
                " email text," +
                " age integer," +
                " password text," +
                " image BLOB)"
        db?.execSQL(sql)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // restoreDB(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        TODO("Sin implementación")
    }

    override fun existsUser(user: User) : Boolean
    {
        val sql : String = "SELECT email FROM users" +
                " where email='${user.email}'"

        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var returnValue : Boolean = result.moveToFirst()

        this.close()
        return returnValue
    }

    override fun verifyUser(user: User) : Boolean
    {
        val sql : String = "SELECT email, password FROM users" +
                           " where email='${user.email}'"

        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var returnValue : Boolean = false

        if(result.moveToFirst())
        {
            if (user.email.equals(result.getString(0)) && user.password.equals(result.getString(1)))
            {
                returnValue = true
            }
        }

        this.close()
        return returnValue
    }

    override fun saveUser(user: User)
    {
        var localUser = ContentValues()
        localUser.put("name", user.name)
        localUser.put("email", user.email)
        localUser.put("age", user.age)
        localUser.put("password", user.password)
        localUser.put("image", user.image)

        this.executeModification(localUser)
    }

    override fun consultUsers(): List<User>?
    {
        val sql : String = "SELECT idUser, name, email, age, password, image FROM users"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var listUsers : MutableList<User>? = ArrayList<User>()
        result.moveToFirst()

        var inside : HashSet<String> = HashSet<String>()

        while(!result.isAfterLast)
        {

            val email = result.getString(2)

            var user : User = User(
                result.getInt(0),
                result.getString(1),
                email,
                result.getInt(3),
                result.getString(4),
                result.getBlob(5)
            )

            if (!inside.contains(email)) {
                listUsers?.add(user)
                inside.add(email)
            }
            result.moveToNext()
        }

        return listUsers
    }


    private fun executeQuery(sql: String, bd : SQLiteDatabase) : Cursor
    {
        val consulta : Cursor = bd.rawQuery(sql,null)
        return consulta
    }

    private fun executeModification(user: ContentValues)
    {
        val bd = this.writableDatabase
        bd.insert("users", null, user)
        bd.close()
    }
}