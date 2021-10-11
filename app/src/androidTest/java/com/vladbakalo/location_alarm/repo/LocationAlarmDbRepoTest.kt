package com.vladbakalo.location_alarm.repo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.vladbakalo.core.db.AppDatabase
import com.vladbakalo.core.db.models.LocationAlarmDb
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class LocationAlarmDbRepoTest {
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
        val alarm = LocationAlarmDb(0, "test1", "address", 20.0, 30.0, "")

        repo.createOrUpdate(alarm)
            .flatMap {
                repo.getLocationAlarmWithAlarmDistancesById(it.id)
            }
            .test()
            .assertNoErrors()
    }

    @Test
    fun testCreateWithExistingName(){
        val alarm = LocationAlarmDb(0, "test1", "address", 20.0, 30.0, "")

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
        val alarm = LocationAlarmDb(0, "test1", "address", 20.0, 30.0, "")
        var newId = 0L
        repo.createOrUpdate(alarm)
            .flatMapCompletable {
                newId = it.id
                repo.deleteLocationAlarmById(newId)
            }
            .test()
            .assertNoErrors()

        repo.getLocationAlarmWithAlarmDistancesById(newId)
            .test()
            .assertError{
                return@assertError true
            }
    }
}