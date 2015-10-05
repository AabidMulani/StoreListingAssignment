package com.coupondunia.assignment;

import android.app.Application;

import com.coupondunia.assignment.interfaces.RetrofitAdapterInterface;
import com.coupondunia.assignment.utils.AppConstants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.okhttp.OkHttpClient;

import java.util.Date;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import timber.log.Timber;

/**
 * Aabid Mulani
 * {04-10-2015}
 */
public class BaseApplication extends Application {

    private RetrofitAdapterInterface retrofitAdapterInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .memoryCacheSize(4 * 1024 * 1024).memoryCacheSizePercentage(25)
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public RetrofitAdapterInterface getWebServiceInterface() {
        if (retrofitAdapterInterface != null) {
            return retrofitAdapterInterface;
        }
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        OkHttpClient okHttpClient = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(AppConstants.BASE_URL)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(okHttpClient))
                .build();

        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        retrofitAdapterInterface = restAdapter.create(RetrofitAdapterInterface.class);
        return retrofitAdapterInterface;
    }


}
