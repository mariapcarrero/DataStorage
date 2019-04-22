package com.example.datastorage.Servicios

class PermissionBuilder() {

    private val permissions : ArrayList<String> = ArrayList()

    fun addPermission(permission : String) : PermissionBuilder {
        permissions.add(permission)
        return this
    }

    fun getPermissions() : ArrayList<String> {
        return permissions
    }
}