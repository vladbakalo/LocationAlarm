package com.vladbakalo.location_alarm.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vladbakalo.location_alarm.application.base.BaseDataModel

@Entity(tableName = "alarm_distance")
data class AlarmDistance(@PrimaryKey(autoGenerate = true) val id: Long,
                         @ColumnInfo(name = "enabled") var enabled: Boolean,
                         @ColumnInfo(name = "location_alarm_id") var locationAlarmId: Long,
                         @ColumnInfo(name = "notify_distance") var notifyDistanceMeters: Int) :
    BaseDataModel, Parcelable {


    constructor(parcel: Parcel) :this(parcel.readLong(), parcel.readByte() != 0.toByte(),
        parcel.readLong(), parcel.readInt())

    override fun getPrimaryId(): Long {
        return id
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeByte(if (enabled) 1 else 0)
        parcel.writeLong(locationAlarmId)
        parcel.writeInt(notifyDistanceMeters)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR :Parcelable.Creator<AlarmDistance> {
        override fun createFromParcel(parcel: Parcel): AlarmDistance {
            return AlarmDistance(parcel)
        }

        override fun newArray(size: Int): Array<AlarmDistance?> {
            return arrayOfNulls(size)
        }
    }
}