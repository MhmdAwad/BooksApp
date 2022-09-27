package com.mhmdawad.booksapp.common.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mhmdawad.booksapp.BuildConfig.BASE_URL
import com.mhmdawad.booksapp.common.data.database.BooksDatabase
import com.mhmdawad.booksapp.common.utils.Constants
import com.mhmdawad.booksapp.discover_books_screen.data.database.BooksDao
import com.mhmdawad.booksapp.discover_books_screen.data.remote.BooksApi
import com.mhmdawad.booksapp.discover_books_screen.data.repository.BooksRepositoryImpl
import com.mhmdawad.booksapp.discover_books_screen.domain.repository.BooksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().connectTimeout(10000L, TimeUnit.SECONDS)
            .readTimeout(10000L, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            })
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideBooksApi(okHttpClient: OkHttpClient): BooksApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(BooksApi::class.java)


    @Provides
    @Singleton
    fun provideBooksDatabase(@ApplicationContext context: Context): BooksDao =
        Room.databaseBuilder(
            context,
            BooksDatabase::class.java,
            Constants.BOOKS_DATABASE_NAME
        )
            .build()
            .booksDao()

    @Provides
    @Singleton
    fun provideBooksRepository(booksDao: BooksDao, booksApi: BooksApi): BooksRepository =
        BooksRepositoryImpl(booksApi, booksDao)
}