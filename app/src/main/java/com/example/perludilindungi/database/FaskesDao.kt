package com.example.perludilindungi.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FaskesDao {
    @Insert
    fun insert(faskes: FaskesEntity)
    @Update
    fun update(faskes: FaskesEntity)
    @Query("DELETE FROM bookmarked_faskes WHERE id = :id")
    fun delete(id: Int)
    @Query("SELECT * FROM bookmarked_faskes ORDER BY id ASC")
    fun getAllFaskes(): List<FaskesEntity>
    @Query("SELECT count(id) FROM bookmarked_faskes WHERE id = :id")
    suspend fun isBookmarked(id: Int): Int
}