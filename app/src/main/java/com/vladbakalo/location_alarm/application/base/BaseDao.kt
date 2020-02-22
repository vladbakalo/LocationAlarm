package com.vladbakalo.location_alarm.application.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Flowable

interface BaseDao<T: BaseDataModel> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRx(model: T): Long

    @Update
    fun update(model: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRx(model: T): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<T>)

    @Delete
    fun delete(model: T)

}