package com.example.firewatch.domain.daos

import androidx.room.*
import com.example.firewatch.domain.entities.Burn
import java.time.LocalDateTime

@Dao
interface BurnDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(burn: Burn)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(burn: Burn)

    @Delete
    fun delete(burn: Burn)

    @Query(
        """
        SELECT * FROM burns
        WHERE
        (:search IS NULL OR title = :search) and
        (:state IS NULL OR state = :state) and
        (:startDate IS NULL OR begin_at >= :startDate) and
        (:endDate IS NULL OR begin_at < :endDate)
        LIMIT :limit OFFSET :offset
    """
    )
    fun getAll(
        search: String? = null,
        state: String? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
        limit: Int = 1,
        offset: Int = 10
    ): List<Burn>
}