package com.example.gradetracker.domain.usecase.subject

import com.example.gradetracker.domain.model.DefaultCategories
import com.example.gradetracker.domain.model.Subject
import com.example.gradetracker.domain.repository.GradeCategoryRepository
import com.example.gradetracker.domain.repository.SubjectRepository

class AddSubjectUseCase(
    private val subjectRepository: SubjectRepository,
    private val gradeCategoryRepository: GradeCategoryRepository
) {
    suspend operator fun invoke(subject: Subject): Long {
        val subjectId = subjectRepository.insertSubject(subject)
        val defaultCategories = DefaultCategories.getDefaultCategories(subjectId)
        gradeCategoryRepository.insertCategories(defaultCategories)
        return subjectId
    }
}
