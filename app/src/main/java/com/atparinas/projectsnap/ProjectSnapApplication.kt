package com.atparinas.projectsnap

import android.app.Application
import com.atparinas.projectsnap.data.ProjectDatabase
import com.atparinas.projectsnap.data.repository.ProjectRepository
import com.atparinas.projectsnap.data.repository.ProjectRepositoryImpl
import com.atparinas.projectsnap.ui.fragment.project.ProjectViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ProjectSnapApplication: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidModule(this@ProjectSnapApplication))

        bind() from singleton { ProjectDatabase(instance()) }
        bind() from singleton { instance<ProjectDatabase>().projectDao() }
        bind<ProjectRepository>() with singleton { ProjectRepositoryImpl(instance()) }
        bind() from provider { ProjectViewModelFactory(instance()) }


    }


}