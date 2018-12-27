package com.atparinas.projectsnap.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration


val MIGRATION_1_2: Migration = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `task_table` (`id` INTEGER NOT NULL, `project_id` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `is_complete` INTEGER NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))")
    }

}

val MIGRATION_2_3: Migration = object : Migration(2,3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `image_table` (`id` INTEGER NOT NULL, `task_id` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `name` TEXT NOT NULL, `uri` TEXT NOT NULL, PRIMARY KEY(`id`))")
    }

}