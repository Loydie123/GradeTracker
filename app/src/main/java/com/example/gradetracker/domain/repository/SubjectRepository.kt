package com.example.gradetracker.domain.repository

import com.example.gradetracker.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {
    fun getAllSubjects(): Flow<List<Subject>>
    suspend fun getSubjectById(id: Long): Subject?
    suspend fun insertSubject(subject: Subject): Long
    suspend fun updateSubject(subject: Subject)
    suspend fun deleteSubject(subject: Subject)
    suspend fun deleteSubjectById(id: Long)
}
