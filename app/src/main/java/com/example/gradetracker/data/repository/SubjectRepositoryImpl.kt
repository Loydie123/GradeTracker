package com.example.gradetracker.data.repository

import com.example.gradetracker.data.local.dao.SubjectDao
import com.example.gradetracker.data.mapper.toDomain
import com.example.gradetracker.data.mapper.toDomainList
import com.example.gradetracker.data.mapper.toEntity
import com.example.gradetracker.domain.model.Subject
import com.example.gradetracker.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectRepositoryImpl(
    private val subjectDao: SubjectDao
) : SubjectRepository {
    
    override fun getAllSubjects(): Flow<List<Subject>> =
        subjectDao.getAllSubjects().map { it.toDomainList() }
    
    override suspend fun getSubjectById(id: Long): Subject? =
        subjectDao.getSubjectById(id)?.toDomain()
    
    override suspend fun insertSubject(subject: Subject): Long =
        subjectDao.insertSubject(subject.toEntity())
    
    override suspend fun updateSubject(subject: Subject) =
        subjectDao.updateSubject(subject.toEntity())
    
    override suspend fun deleteSubject(subject: Subject) =
        subjectDao.deleteSubject(subject.toEntity())
    
    override suspend fun deleteSubjectById(id: Long) =
        subjectDao.deleteSubjectById(id)
}
