package com.agzz.transformerswars.di

import com.agzz.transformerswars.repository.AllSparkRepository
import com.agzz.transformerswars.repository.TransformersRepository
import com.agzz.transformerswars.repository.impl.AllSparkRepositoryImpl
import com.agzz.transformerswars.repository.impl.TransformersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTransformersRepository(repository: TransformersRepositoryImpl): TransformersRepository = repository

    @Singleton
    @Provides
    fun provideAllSparkRepository(repository: AllSparkRepositoryImpl): AllSparkRepository = repository
}