package com.example.firewatch.domain.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firewatch.domain.entities.BaseUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(user: BaseUser)

    @Query("SELECT * FROM users where id = :id")
    fun getById(id: String): BaseUser?
}