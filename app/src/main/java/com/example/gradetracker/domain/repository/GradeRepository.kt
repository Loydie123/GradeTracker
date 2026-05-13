package com.example.gradetracker.domain.repository

import com.example.gradetracker.domain.model.Grade
import kotlinx.coroutines.flow.Flow

interface GradeRepository {
    fun getGradesBySubject(subjectId: Long): Flow<List<Grade>>
    fun getGradesByCategory(subjectId: Long, category: String): Flow<List<Grade>>
    fun getAllGrades(): Flow<List<Grade>>
    suspend fun getGradeById(id: Long): Grade?
    suspend fun insertGrade(grade: Grade): Long
    suspend fun updateGrade(grade: Grade)
    suspend fun deleteGrade(grade: Grade)
    suspend fun deleteGradeById(id: Long)
}
