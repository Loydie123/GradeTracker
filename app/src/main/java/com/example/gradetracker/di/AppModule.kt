package com.example.gradetracker.di

import android.content.Context
import com.example.gradetracker.data.local.database.GradeTrackerDatabase
import com.example.gradetracker.data.repository.GradeCategoryRepositoryImpl
import com.example.gradetracker.data.repository.GradeRepositoryImpl
import com.example.gradetracker.data.repository.SubjectRepositoryImpl
import com.example.gradetracker.domain.repository.GradeCategoryRepository
import com.example.gradetracker.domain.repository.GradeRepository
import com.example.gradetracker.domain.repository.SubjectRepository
import com.example.gradetracker.domain.usecase.category.GetCategoriesBySubjectUseCase
import com.example.gradetracker.domain.usecase.category.UpdateCategoryUseCase
import com.example.gradetracker.domain.usecase.gpa.CalculateGpaUseCase
import com.example.gradetracker.domain.usecase.grade.AddGradeUseCase
import com.example.gradetracker.domain.usecase.grade.DeleteGradeUseCase
import com.example.gradetracker.domain.usecase.grade.GetAllGradesUseCase
import com.example.gradetracker.domain.usecase.grade.GetGradesBySubjectUseCase
import com.example.gradetracker.domain.usecase.grade.UpdateGradeUseCase
import com.example.gradetracker.domain.usecase.prediction.PredictGradeUseCase
import com.example.gradetracker.domain.usecase.subject.AddSubjectUseCase
import com.example.gradetracker.domain.usecase.subject.DeleteSubjectUseCase
import com.example.gradetracker.domain.usecase.subject.GetAllSubjectsUseCase
import com.example.gradetracker.domain.usecase.subject.GetSubjectByIdUseCase
import com.example.gradetracker.domain.usecase.subject.UpdateSubjectUseCase

object AppModule {
    
    private var database: GradeTrackerDatabase? = null
    
    private fun getDatabase(context: Context): GradeTrackerDatabase {
        return database ?: GradeTrackerDatabase.getDatabase(context).also { database = it }
    }
    
    fun provideSubjectRepository(context: Context): SubjectRepository {
        return SubjectRepositoryImpl(getDatabase(context).subjectDao())
    }
    
    fun provideGradeRepository(context: Context): GradeRepository {
        return GradeRepositoryImpl(getDatabase(context).gradeDao())
    }
    
    fun provideGradeCategoryRepository(context: Context): GradeCategoryRepository {
        return GradeCategoryRepositoryImpl(getDatabase(context).gradeCategoryDao())
    }
    
    fun provideGetAllSubjectsUseCase(context: Context): GetAllSubjectsUseCase {
        return GetAllSubjectsUseCase(provideSubjectRepository(context))
    }
    
    fun provideAddSubjectUseCase(context: Context): AddSubjectUseCase {
        return AddSubjectUseCase(
            provideSubjectRepository(context),
            provideGradeCategoryRepository(context)
        )
    }
    
    fun provideUpdateSubjectUseCase(context: Context): UpdateSubjectUseCase {
        return UpdateSubjectUseCase(provideSubjectRepository(context))
    }
    
    fun provideDeleteSubjectUseCase(context: Context): DeleteSubjectUseCase {
        return DeleteSubjectUseCase(provideSubjectRepository(context))
    }
    
    fun provideGetSubjectByIdUseCase(context: Context): GetSubjectByIdUseCase {
        return GetSubjectByIdUseCase(provideSubjectRepository(context))
    }
    
    fun provideGetGradesBySubjectUseCase(context: Context): GetGradesBySubjectUseCase {
        return GetGradesBySubjectUseCase(provideGradeRepository(context))
    }
    
    fun provideAddGradeUseCase(context: Context): AddGradeUseCase {
        return AddGradeUseCase(provideGradeRepository(context))
    }
    
    fun provideUpdateGradeUseCase(context: Context): UpdateGradeUseCase {
        return UpdateGradeUseCase(provideGradeRepository(context))
    }
    
    fun provideDeleteGradeUseCase(context: Context): DeleteGradeUseCase {
        return DeleteGradeUseCase(provideGradeRepository(context))
    }
    
    fun provideGetAllGradesUseCase(context: Context): GetAllGradesUseCase {
        return GetAllGradesUseCase(provideGradeRepository(context))
    }
    
    fun provideGetCategoriesBySubjectUseCase(context: Context): GetCategoriesBySubjectUseCase {
        return GetCategoriesBySubjectUseCase(provideGradeCategoryRepository(context))
    }
    
    fun provideUpdateCategoryUseCase(context: Context): UpdateCategoryUseCase {
        return UpdateCategoryUseCase(provideGradeCategoryRepository(context))
    }
    
    fun provideCalculateGpaUseCase(): CalculateGpaUseCase {
        return CalculateGpaUseCase()
    }
    
    fun providePredictGradeUseCase(): PredictGradeUseCase {
        return PredictGradeUseCase()
    }
}
