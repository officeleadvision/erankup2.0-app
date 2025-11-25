package com.erankup.customersatisfaction.data.remote;

import com.erankup.customersatisfaction.util.DeviceConfigManager;
import com.google.gson.Gson;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class VoteApiProvider_Factory implements Factory<VoteApiProvider> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<DeviceConfigManager> deviceConfigManagerProvider;

  public VoteApiProvider_Factory(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Gson> gsonProvider, Provider<DeviceConfigManager> deviceConfigManagerProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.gsonProvider = gsonProvider;
    this.deviceConfigManagerProvider = deviceConfigManagerProvider;
  }

  @Override
  public VoteApiProvider get() {
    return newInstance(okHttpClientProvider.get(), gsonProvider.get(), deviceConfigManagerProvider.get());
  }

  public static VoteApiProvider_Factory create(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Gson> gsonProvider, Provider<DeviceConfigManager> deviceConfigManagerProvider) {
    return new VoteApiProvider_Factory(okHttpClientProvider, gsonProvider, deviceConfigManagerProvider);
  }

  public static VoteApiProvider newInstance(OkHttpClient okHttpClient, Gson gson,
      DeviceConfigManager deviceConfigManager) {
    return new VoteApiProvider(okHttpClient, gson, deviceConfigManager);
  }
}
