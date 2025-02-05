package com.example.mycalendar
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

    @Dao
    interface ImageDao {
        @Query("SELECT * FROM images WHERE eventId = :eventId")
        suspend fun getImagesByEventId(eventId: Long): List<ImageEntity>

        @Insert
        suspend fun insert(image: ImageEntity)

        @Query("SELECT * FROM images")
        suspend fun getAllImages(): List<ImageEntity>

        @Query("DELETE FROM images WHERE id = :id")
        suspend fun deleteImage(id: Long)
    }

