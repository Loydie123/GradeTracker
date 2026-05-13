package com.example.gradetracker.domain.usecase.category

import com.example.gradetracker.domain.model.GradeCategory
import com.example.gradetracker.domain.repository.GradeCategoryRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesBySubjectUseCase(
    private val gradeCategoryRepository: GradeCategoryRepository
) {
    operator fun invoke(subjectId: Long): Flow<List<GradeCategory>> =
        gradeCategoryRepository.getCategoriesBySubject(subjectId)
}
