package com.example.gradetracker.domain.repository

import com.example.gradetracker.domain.model.GradeCategory
import kotlinx.coroutines.flow.Flow

interface GradeCategoryRepository {
    fun getCategoriesBySubject(subjectId: Long): Flow<List<GradeCategory>>
    suspend fun getCategoryById(id: Long): GradeCategory?
    suspend fun insertCategory(category: GradeCategory): Long
    suspend fun insertCategories(categories: List<GradeCategory>)
    suspend fun updateCategory(category: GradeCategory)
    suspend fun deleteCategory(category: GradeCategory)
    suspend fun deleteCategoriesBySubject(subjectId: Long)
}
