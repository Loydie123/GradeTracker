package com.example.gradetracker.domain.usecase.category

import com.example.gradetracker.domain.model.GradeCategory
import com.example.gradetracker.domain.repository.GradeCategoryRepository

class UpdateCategoryUseCase(
    private val gradeCategoryRepository: GradeCategoryRepository
) {
    suspend operator fun invoke(category: GradeCategory) = 
        gradeCategoryRepository.updateCategory(category)
}
