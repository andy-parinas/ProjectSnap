package com.atparinas.projectsnap.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.atparinas.projectsnap.data.dao.ProjectDao
import com.atparinas.projectsnap.data.dao.TaskDao
import com.atparinas.projectsnap.data.entity.Project
import com.atparinas.projectsnap.data.entity.Task

@Database(entities = [Project::class, Task::class], version = 2)
@TypeConverters(Converters::class)
abstract class ProjectDatabase: RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile private var instance: ProjectDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, ProjectDatabase::class.java, "projectsnap.db")
                .addMigrations(MIGRATION_1_2)
                .build()

    }

}