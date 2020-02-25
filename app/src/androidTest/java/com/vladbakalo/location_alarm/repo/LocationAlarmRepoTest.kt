package com.vladbakalo.location_alarm.repo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.vladbakalo.location_alarm.data.AppDatabase
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.lang.IllegalArgumentException

@RunWith(JUnit4::class)
class LocationAlarmRepoTest {
    lateinit var context: Context
    lateinit var db: AppDatabase
    lateinit var repo: LocationAlarmRepository

    @Before
    fun setUp(){
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        repo = LocationAlarmRepository(db, context)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testCreate(){
        val alarm = LocationAlarm(0, "test1", "address", 20.0, 30.0, "")

        repo.createOrUpdate(alarm)
            .flatMap {
                repo.getLocationAlarm(it.id)
            }
            .test()
            .assertNoErrors()
    }

    @Test
    fun testCreateWithExistingName(){
        val alarm = LocationAlarm(0, "test1", "address", 20.0, 30.0, "")

        repo.createOrUpdate(alarm)
            .test()
            .assertValue {
                it.name == alarm.name
            }

        repo.createOrUpdate(alarm)
            .test()
            .assertError {
                it is IllegalArgumentException
            }
    }

    @Test
    fun testDeletion(){
        val alarm = LocationAlarm(0, "test1", "address", 20.0, 30.0, "")
        var newId = 0L
        repo.createOrUpdate(alarm)
            .flatMapCompletable {
                newId = it.id
                repo.deleteLocationAlarm(newId)
            }
            .test()
            .assertNoErrors()

        repo.getLocationAlarm(newId)
            .test()
            .assertError{
                return@assertError true
            }
    }
}