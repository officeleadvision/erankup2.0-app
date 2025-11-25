package com.erankup.customersatisfaction.util;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DeviceConfigManager_Factory implements Factory<DeviceConfigManager> {
  private final Provider<Context> contextProvider;

  public DeviceConfigManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DeviceConfigManager get() {
    return newInstance(contextProvider.get());
  }

  public static DeviceConfigManager_Factory create(Provider<Context> contextProvider) {
    return new DeviceConfigManager_Factory(contextProvider);
  }

  public static DeviceConfigManager newInstance(Context context) {
    return new DeviceConfigManager(context);
  }
}
