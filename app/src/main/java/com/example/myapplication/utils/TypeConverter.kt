package com.example.myapplication.utils

import androidx.room.TypeConverter
import com.example.myapplication.model.*
import com.google.gson.Gson

class TypeConverter {

    @TypeConverter
    fun dobToJson(dob: Dob): String {
        return Gson().toJson(dob)
    }

    @TypeConverter
    fun dobFromJson(dob: String): Dob {
        return Gson().fromJson(dob, Dob::class.java)
    }

    @TypeConverter
    fun nameToJson(name: Name): String {
        return Gson().toJson(name)
    }

    @TypeConverter
    fun nameFromJson(name: String): Name {
        return Gson().fromJson(name, Name::class.java)
    }

    @TypeConverter
    fun registeredToJson(registered: Registered): String {
        return Gson().toJson(registered)
    }

    @TypeConverter
    fun registeredFromJson(registered: String): Registered? {
        return Gson().fromJson(registered, Registered::class.java)
    }

    @TypeConverter
    fun locationToJson(location: Location): String {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun locationFromJson(location: String): Location? {
        return Gson().fromJson(location, Location::class.java)
    }

    @TypeConverter
    fun idToJson(id: Id): String {
        return Gson().toJson(id)
    }

    @TypeConverter
    fun idFromJson(id: String): Id? {
        return Gson().fromJson(id, Id::class.java)
    }

    @TypeConverter
    fun loginToJson(login: Login): String {
        return Gson().toJson(login)
    }

    @TypeConverter
    fun loginFromJson(login: String): Login? {
        return Gson().fromJson(login, Login::class.java)
    }

    @TypeConverter
    fun pictureToJson(picture: Picture): String {
        return Gson().toJson(picture)
    }

    @TypeConverter
    fun pictureFromJson(picture: String): Picture? {
        return Gson().fromJson(picture, Picture::class.java)
    }
}
