package com.example.gradetracker.data.repository

import com.example.gradetracker.data.local.dao.GradeDao
import com.example.gradetracker.data.mapper.toDomain
import com.example.gradetracker.data.mapper.toDomainList
import com.example.gradetracker.data.mapper.toEntity
import com.example.gradetracker.domain.model.Grade
import com.example.gradetracker.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GradeRepositoryImpl(
    private val gradeDao: GradeDao
) : GradeRepository {
    
    override fun getGradesBySubject(subjectId: Long): Flow<List<Grade>> =
        gradeDao.getGradesBySubject(subjectId).map { it.toDomainList() }
    
    override fun getGradesByCategory(subjectId: Long, category: String): Flow<List<Grade>> =
        gradeDao.getGradesByCategory(subjectId, category).map { it.toDomainList() }
    
    override fun getAllGrades(): Flow<List<Grade>> =
        gradeDao.getAllGrades().map { it.toDomainList() }
    
    override suspend fun getGradeById(id: Long): Grade? =
        gradeDao.getGradeById(id)?.toDomain()
    
    override suspend fun insertGrade(grade: Grade): Long =
        gradeDao.insertGrade(grade.toEntity())
    
    override suspend fun updateGrade(grade: Grade) =
        gradeDao.updateGrade(grade.toEntity())
    
    override suspend fun deleteGrade(grade: Grade) =
        gradeDao.deleteGrade(grade.toEntity())
    
    override suspend fun deleteGradeById(id: Long) =
        gradeDao.deleteGradeById(id)
}
