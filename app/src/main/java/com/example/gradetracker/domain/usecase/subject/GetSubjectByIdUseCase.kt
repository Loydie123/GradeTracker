package com.example.gradetracker.domain.usecase.subject

import com.example.gradetracker.domain.model.Subject
import com.example.gradetracker.domain.repository.SubjectRepository

class GetSubjectByIdUseCase(
    private val subjectRepository: SubjectRepository
) {
    suspend operator fun invoke(id: Long): Subject? = subjectRepository.getSubjectById(id)
}
