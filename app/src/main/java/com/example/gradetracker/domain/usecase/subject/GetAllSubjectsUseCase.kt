package com.example.gradetracker.domain.usecase.subject

import com.example.gradetracker.domain.model.Subject
import com.example.gradetracker.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow

class GetAllSubjectsUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(): Flow<List<Subject>> = subjectRepository.getAllSubjects()
}
