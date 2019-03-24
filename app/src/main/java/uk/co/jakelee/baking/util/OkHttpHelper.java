package uk.co.jakelee.baking.util;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class OkHttpHelper {
    private static final int TIMEOUT_SECONDS = 20;

    public OkHttpClient httpClient(Context context) {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        File httpCacheDirectory = new File(context.getCacheDir(), "http-cache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
    }

    public class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(1, TimeUnit.HOURS)
                    .build();
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheControl.toString())
                    .build();
        }
    }
}
