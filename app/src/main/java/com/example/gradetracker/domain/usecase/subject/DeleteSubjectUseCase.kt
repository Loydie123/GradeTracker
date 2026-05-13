package com.example.gradetracker.domain.usecase.subject

import com.example.gradetracker.domain.repository.SubjectRepository

class DeleteSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {
    suspend operator fun invoke(id: Long) = subjectRepository.deleteSubjectById(id)
}
