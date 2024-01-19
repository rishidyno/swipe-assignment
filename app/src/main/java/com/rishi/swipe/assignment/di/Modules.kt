package com.rishi.swipe.assignment.di

import android.content.Context
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.rishi.swipe.assignment.model.database.ProductDao
import com.rishi.swipe.assignment.model.database.DatabaseRepository
import com.rishi.swipe.assignment.model.database.ProductRoomDatabase
import com.rishi.swipe.assignment.model.network.ProductApiService
import com.rishi.swipe.assignment.model.network.NetworkRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    private const val BASE_URL = "https://app.getswipe.in/api/public/"

    @Provides
    @Singleton
    fun provideOkhttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(OkHttpProfilerInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideNetworkRepository(productApiService: ProductApiService): NetworkRepository {
        return NetworkRepository(productApiService, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideProductDao(database: ProductRoomDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideProductRoomDataBase(@ApplicationContext applicationContext: Context): ProductRoomDatabase {
        return ProductRoomDatabase.getDatabase(applicationContext)
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(productDao: ProductDao): DatabaseRepository {
        return DatabaseRepository(productDao)
    }
}