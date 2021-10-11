package com.vladbakalo.core.db.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T:BaseDataModel> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(model: T): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<T>)

    @Delete
    suspend fun delete(model: T)
}