package com.mojapka.amw_prime.models
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recruitment")
data class Recruitment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)


