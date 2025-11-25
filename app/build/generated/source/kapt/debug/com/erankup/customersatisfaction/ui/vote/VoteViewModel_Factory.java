package com.erankup.customersatisfaction.ui.vote;

import com.erankup.customersatisfaction.data.repository.VoteRepository;
import com.erankup.customersatisfaction.util.DeviceConfigManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata
@QualifierMetadata("com.erankup.customersatisfaction.di.IoDispatcher")
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
public final class VoteViewModel_Factory implements Factory<VoteViewModel> {
  private final Provider<VoteRepository> repositoryProvider;

  private final Provider<DeviceConfigManager> deviceConfigManagerProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public VoteViewModel_Factory(Provider<VoteRepository> repositoryProvider,
      Provider<DeviceConfigManager> deviceConfigManagerProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.repositoryProvider = repositoryProvider;
    this.deviceConfigManagerProvider = deviceConfigManagerProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public VoteViewModel get() {
    return newInstance(repositoryProvider.get(), deviceConfigManagerProvider.get(), ioDispatcherProvider.get());
  }

  public static VoteViewModel_Factory create(Provider<VoteRepository> repositoryProvider,
      Provider<DeviceConfigManager> deviceConfigManagerProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new VoteViewModel_Factory(repositoryProvider, deviceConfigManagerProvider, ioDispatcherProvider);
  }

  public static VoteViewModel newInstance(VoteRepository repository,
      DeviceConfigManager deviceConfigManager, CoroutineDispatcher ioDispatcher) {
    return new VoteViewModel(repository, deviceConfigManager, ioDispatcher);
  }
}
