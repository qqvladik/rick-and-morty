package by.mankevich.rickandmorty.library.db.converter

import androidx.room.TypeConverter
import by.mankevich.rickandmorty.library.db.entity.Location
import com.google.gson.Gson

class LocationConverter {
    @TypeConverter
    fun fromLocation(location: Location): String {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun toLocation(location: String): Location {
        return Gson().fromJson(location, Location::class.java)
    }
}