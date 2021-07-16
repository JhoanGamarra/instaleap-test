package com.jhoangamarra.instaleaptest.data.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "type")
    val type: String = "",
)