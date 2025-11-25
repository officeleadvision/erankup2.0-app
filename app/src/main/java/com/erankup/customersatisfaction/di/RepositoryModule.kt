package com.erankup.customersatisfaction.di

import com.erankup.customersatisfaction.data.remote.VoteApiProvider
import com.erankup.customersatisfaction.data.repository.VoteRepository
import com.erankup.customersatisfaction.data.repository.VoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideVoteRepository(
        voteApiProvider: VoteApiProvider
    ): VoteRepository = VoteRepositoryImpl(voteApiProvider)
}

