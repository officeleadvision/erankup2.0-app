package com.erankup.customersatisfaction.data.remote

import android.util.Log
import com.erankup.customersatisfaction.util.DeviceConfigManager
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import okhttp3.OkHttpClient
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate

@Singleton
class VoteApiProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson,
    private val deviceConfigManager: DeviceConfigManager
) {

    @Volatile
    private var cachedBaseUrl: String? = null

    @Volatile
    private var cachedApi: VoteApi? = null

    fun getApi(): VoteApi {
        val currentBaseUrl = deviceConfigManager.getEffectiveBaseUrl()
        val existingApi = cachedApi
        if (existingApi != null && cachedBaseUrl == currentBaseUrl) {
            return existingApi
        }

        return synchronized(this) {
            val refreshedBaseUrl = deviceConfigManager.getEffectiveBaseUrl()
            val cached = cachedApi
            if (cached != null && cachedBaseUrl == refreshedBaseUrl) {
                cached
            } else {
                val httpClient = okHttpClient.newBuilder()
                    .applyTrustAllForBaseUrl(refreshedBaseUrl)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(refreshedBaseUrl)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                val api = retrofit.create(VoteApi::class.java)
                cachedBaseUrl = refreshedBaseUrl
                cachedApi = api
                api
            }
        }
    }

    private fun OkHttpClient.Builder.applyTrustAllForBaseUrl(baseUrl: String): OkHttpClient.Builder {
        val host = baseUrl.toHttpUrlOrNull()?.host ?: return this
        return runCatching {
            val trustManager = object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
                override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
            }
            val trustManagers = arrayOf<TrustManager>(trustManager)
            val sslContext = SSLContext.getInstance("TLS").apply {
                init(null, trustManagers, SecureRandom())
            }
            sslSocketFactory(sslContext.socketFactory, trustManager)
            hostnameVerifier { hostname, session ->
                hostname.equals(host, ignoreCase = true) ||
                    HttpsURLConnection.getDefaultHostnameVerifier().verify(hostname, session)
            }
            this
        }.onFailure {
            Log.w("VoteApiProvider", "Falling back to default SSL configuration: ${it.message}")
        }.getOrElse { this }
    }
}

