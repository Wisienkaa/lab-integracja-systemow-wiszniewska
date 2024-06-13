package com.mojapka.amw_prime.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mojapka.amw_prime.models.Recruitment
@Dao
interface RecruitmentDao {
    @Insert
    suspend fun insert(recruitment: Recruitment)

    @Query("SELECT * FROM recruitment")
    suspend fun getAll(): List<Recruitment>

}

