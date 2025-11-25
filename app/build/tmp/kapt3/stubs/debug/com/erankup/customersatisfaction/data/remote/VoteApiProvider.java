package com.erankup.customersatisfaction.data.remote;

import android.util.Log;
import com.erankup.customersatisfaction.util.DeviceConfigManager;
import com.google.gson.Gson;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\r\u001a\u00020\nJ\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\fH\u0002R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/erankup/customersatisfaction/data/remote/VoteApiProvider;", "", "okHttpClient", "Lokhttp3/OkHttpClient;", "gson", "Lcom/google/gson/Gson;", "deviceConfigManager", "Lcom/erankup/customersatisfaction/util/DeviceConfigManager;", "(Lokhttp3/OkHttpClient;Lcom/google/gson/Gson;Lcom/erankup/customersatisfaction/util/DeviceConfigManager;)V", "cachedApi", "Lcom/erankup/customersatisfaction/data/remote/VoteApi;", "cachedBaseUrl", "", "getApi", "applyTrustAllForBaseUrl", "Lokhttp3/OkHttpClient$Builder;", "baseUrl", "app_debug"})
public final class VoteApiProvider {
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient okHttpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final com.erankup.customersatisfaction.util.DeviceConfigManager deviceConfigManager = null;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private volatile java.lang.String cachedBaseUrl;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private volatile com.erankup.customersatisfaction.data.remote.VoteApi cachedApi;
    
    @javax.inject.Inject()
    public VoteApiProvider(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient okHttpClient, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson, @org.jetbrains.annotations.NotNull()
    com.erankup.customersatisfaction.util.DeviceConfigManager deviceConfigManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.erankup.customersatisfaction.data.remote.VoteApi getApi() {
        return null;
    }
    
    private final okhttp3.OkHttpClient.Builder applyTrustAllForBaseUrl(okhttp3.OkHttpClient.Builder $this$applyTrustAllForBaseUrl, java.lang.String baseUrl) {
        return null;
    }
}