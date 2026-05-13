package com.example.gradetracker.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gradetracker.data.local.dao.GradeCategoryDao
import com.example.gradetracker.data.local.dao.GradeDao
import com.example.gradetracker.data.local.dao.SubjectDao
import com.example.gradetracker.data.local.entity.GradeCategoryEntity
import com.example.gradetracker.data.local.entity.GradeEntity
import com.example.gradetracker.data.local.entity.SubjectEntity

@Database(
    entities = [
        SubjectEntity::class,
        GradeEntity::class,
        GradeCategoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GradeTrackerDatabase : RoomDatabase() {
    
    abstract fun subjectDao(): SubjectDao
    abstract fun gradeDao(): GradeDao
    abstract fun gradeCategoryDao(): GradeCategoryDao
    
    companion object {
        @Volatile
        private var INSTANCE: GradeTrackerDatabase? = null
        
        fun getDatabase(context: Context): GradeTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GradeTrackerDatabase::class.java,
                    "grade_tracker_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
