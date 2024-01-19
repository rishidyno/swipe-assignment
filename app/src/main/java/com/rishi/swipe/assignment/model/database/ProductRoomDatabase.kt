package com.rishi.swipe.assignment.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rishi.swipe.assignment.model.entity.ProductEntity

/**
 * Room Database class for storing products.
 * It is annotated with [@Database] to specify the entities it contains and the version of the database.
 *
 * @property entities List of entity classes that this database holds, in this case, only [ProductEntity].
 * @property version Version of the database.
 *
 * @see Database
 * @see RoomDatabase
 */
@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductRoomDatabase : RoomDatabase() {

    /**
     * Abstract function to retrieve the DAO (Data Access Object) for products.
     *
     * @return ProductDao instance
     */
    abstract fun productDao(): ProductDao

    companion object {
        // Singleton prevents multiple instances of the database opening at the same time.
        @Volatile
        private var INSTANCE: ProductRoomDatabase? = null

        /**
         * Get an instance of the ProductRoomDatabase.
         *
         * @param context Application context
         * @return ProductRoomDatabase instance
         */
        fun getDatabase(context: Context): ProductRoomDatabase {
            // If the INSTANCE is not null, then return it,
            // If it is null, then create the database
            return INSTANCE ?: synchronized(lock = this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductRoomDatabase::class.java,
                    "products_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // Return instance
                instance
            }
        }
    }
}
