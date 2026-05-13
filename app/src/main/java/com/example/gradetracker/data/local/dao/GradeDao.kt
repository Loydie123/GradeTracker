package com.example.gradetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gradetracker.data.local.entity.GradeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GradeDao {
    
    @Query("SELECT * FROM grades WHERE subjectId = :subjectId ORDER BY createdAt DESC")
    fun getGradesBySubject(subjectId: Long): Flow<List<GradeEntity>>
    
    @Query("SELECT * FROM grades WHERE subjectId = :subjectId AND category = :category")
    fun getGradesByCategory(subjectId: Long, category: String): Flow<List<GradeEntity>>
    
    @Query("SELECT * FROM grades WHERE id = :id")
    suspend fun getGradeById(id: Long): GradeEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrade(grade: GradeEntity): Long
    
    @Update
    suspend fun updateGrade(grade: GradeEntity)
    
    @Delete
    suspend fun deleteGrade(grade: GradeEntity)
    
    @Query("DELETE FROM grades WHERE id = :id")
    suspend fun deleteGradeById(id: Long)
    
    @Query("SELECT * FROM grades ORDER BY createdAt DESC")
    fun getAllGrades(): Flow<List<GradeEntity>>
}
