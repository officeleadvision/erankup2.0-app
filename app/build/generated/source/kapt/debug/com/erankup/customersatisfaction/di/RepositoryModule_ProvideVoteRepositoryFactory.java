package com.erankup.customersatisfaction.di;

import com.erankup.customersatisfaction.data.remote.VoteApiProvider;
import com.erankup.customersatisfaction.data.repository.VoteRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class RepositoryModule_ProvideVoteRepositoryFactory implements Factory<VoteRepository> {
  private final Provider<VoteApiProvider> voteApiProvider;

  public RepositoryModule_ProvideVoteRepositoryFactory(Provider<VoteApiProvider> voteApiProvider) {
    this.voteApiProvider = voteApiProvider;
  }

  @Override
  public VoteRepository get() {
    return provideVoteRepository(voteApiProvider.get());
  }

  public static RepositoryModule_ProvideVoteRepositoryFactory create(
      Provider<VoteApiProvider> voteApiProvider) {
    return new RepositoryModule_ProvideVoteRepositoryFactory(voteApiProvider);
  }

  public static VoteRepository provideVoteRepository(VoteApiProvider voteApiProvider) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideVoteRepository(voteApiProvider));
  }
}
