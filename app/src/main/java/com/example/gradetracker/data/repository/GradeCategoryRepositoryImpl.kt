package com.example.gradetracker.data.repository

import com.example.gradetracker.data.local.dao.GradeCategoryDao
import com.example.gradetracker.data.mapper.toDomain
import com.example.gradetracker.data.mapper.toDomainList
import com.example.gradetracker.data.mapper.toEntity
import com.example.gradetracker.data.mapper.toEntityList
import com.example.gradetracker.domain.model.GradeCategory
import com.example.gradetracker.domain.repository.GradeCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GradeCategoryRepositoryImpl(
    private val gradeCategoryDao: GradeCategoryDao
) : GradeCategoryRepository {
    
    override fun getCategoriesBySubject(subjectId: Long): Flow<List<GradeCategory>> =
        gradeCategoryDao.getCategoriesBySubject(subjectId).map { it.toDomainList() }
    
    override suspend fun getCategoryById(id: Long): GradeCategory? =
        gradeCategoryDao.getCategoryById(id)?.toDomain()
    
    override suspend fun insertCategory(category: GradeCategory): Long =
        gradeCategoryDao.insertCategory(category.toEntity())
    
    override suspend fun insertCategories(categories: List<GradeCategory>) =
        gradeCategoryDao.insertCategories(categories.toEntityList())
    
    override suspend fun updateCategory(category: GradeCategory) =
        gradeCategoryDao.updateCategory(category.toEntity())
    
    override suspend fun deleteCategory(category: GradeCategory) =
        gradeCategoryDao.deleteCategory(category.toEntity())
    
    override suspend fun deleteCategoriesBySubject(subjectId: Long) =
        gradeCategoryDao.deleteCategoriesBySubject(subjectId)
}
