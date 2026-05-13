package com.example.gradetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gradetracker.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    
    @Query("SELECT * FROM subjects ORDER BY createdAt DESC")
    fun getAllSubjects(): Flow<List<SubjectEntity>>
    
    @Query("SELECT * FROM subjects WHERE id = :id")
    suspend fun getSubjectById(id: Long): SubjectEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity): Long
    
    @Update
    suspend fun updateSubject(subject: SubjectEntity)
    
    @Delete
    suspend fun deleteSubject(subject: SubjectEntity)
    
    @Query("DELETE FROM subjects WHERE id = :id")
    suspend fun deleteSubjectById(id: Long)
}
