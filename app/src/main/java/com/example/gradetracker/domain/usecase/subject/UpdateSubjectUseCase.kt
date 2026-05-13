package com.example.gradetracker.domain.usecase.subject

import com.example.gradetracker.domain.model.Subject
import com.example.gradetracker.domain.repository.SubjectRepository

class UpdateSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {
    suspend operator fun invoke(subject: Subject) = subjectRepository.updateSubject(subject)
}
