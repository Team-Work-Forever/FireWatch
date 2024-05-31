package com.example.firewatch.domain.daos

import androidx.room.*
import com.example.firewatch.domain.entities.Autarchy

@Dao
interface AutarchyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(autarchy: Autarchy)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(autarchy: Autarchy)

    @Delete
    fun delete(autarchy: Autarchy)

    @Query("SELECT * FROM autarchies where id = :id")
    fun getById(id: String): Autarchy?

    @Query(
        """
        Select * FROM autarchies
        WHERE
        (:search IS NULL OR title = :search)
        LIMIT :limit OFFSET :offset
    """
    )
    fun getAll(
        search: String? = null,
        limit: Int,
        offset: Int,
    ): List<Autarchy>
}