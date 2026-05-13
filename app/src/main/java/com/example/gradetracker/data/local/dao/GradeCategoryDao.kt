package com.example.gradetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gradetracker.data.local.entity.GradeCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GradeCategoryDao {
    
    @Query("SELECT * FROM grade_categories WHERE subjectId = :subjectId")
    fun getCategoriesBySubject(subjectId: Long): Flow<List<GradeCategoryEntity>>
    
    @Query("SELECT * FROM grade_categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): GradeCategoryEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: GradeCategoryEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<GradeCategoryEntity>)
    
    @Update
    suspend fun updateCategory(category: GradeCategoryEntity)
    
    @Delete
    suspend fun deleteCategory(category: GradeCategoryEntity)
    
    @Query("DELETE FROM grade_categories WHERE subjectId = :subjectId")
    suspend fun deleteCategoriesBySubject(subjectId: Long)
}
