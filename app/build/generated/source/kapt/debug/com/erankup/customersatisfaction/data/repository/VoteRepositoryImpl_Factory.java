package com.erankup.customersatisfaction.data.repository;

import com.erankup.customersatisfaction.data.remote.VoteApiProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class VoteRepositoryImpl_Factory implements Factory<VoteRepositoryImpl> {
  private final Provider<VoteApiProvider> voteApiProvider;

  public VoteRepositoryImpl_Factory(Provider<VoteApiProvider> voteApiProvider) {
    this.voteApiProvider = voteApiProvider;
  }

  @Override
  public VoteRepositoryImpl get() {
    return newInstance(voteApiProvider.get());
  }

  public static VoteRepositoryImpl_Factory create(Provider<VoteApiProvider> voteApiProvider) {
    return new VoteRepositoryImpl_Factory(voteApiProvider);
  }

  public static VoteRepositoryImpl newInstance(VoteApiProvider voteApiProvider) {
    return new VoteRepositoryImpl(voteApiProvider);
  }
}
