package com.vladbakalo.location_alarm.repo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.vladbakalo.core.db.AppDatabase
import com.vladbakalo.core.db.models.AlarmDistanceDb
import com.vladbakalo.core.db.repo.AlarmDistanceRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class AlarmRepoTest {
    lateinit var context: Context
    lateinit var db: AppDatabase
    lateinit var repo: AlarmDistanceRepository

    @Before
    fun setUp(){
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        repo = AlarmDistanceRepository(db)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testCreation() {
        val locationAlarmId = 1L
        val alarmList = arrayOf(AlarmDistanceDb(0, true, locationAlarmId, 100),
            AlarmDistanceDb(0, true, locationAlarmId, 200)).toList()
        repo.createOrUpdateAlarmsById(alarmList, locationAlarmId)
            .test()
            .assertNoErrors()

        assert(repo.findAll()
            .isNotEmpty())
    }

    @Test
    fun testDeletion() {
        val locationAlarmId = 1L
        val alarmList = arrayOf(AlarmDistanceDb(0, true, locationAlarmId, 100),
            AlarmDistanceDb(0, true, locationAlarmId, 200)).toList()
        repo.createOrUpdateAlarmsById(alarmList, locationAlarmId)
            .test()
            .assertNoErrors()

        repo.deleteAlarmDistancesByLocationAlarmId(locationAlarmId)
            .test()
            .assertNoErrors()

        assert(repo.findAll()
            .isEmpty())
    }

    @Test
    fun testDeletionV2() {
        val locationAlarmId = 1L
        val locationAlarmId2 = 2L
        val alarmList = arrayOf(AlarmDistanceDb(0, true, locationAlarmId, 100),
            AlarmDistanceDb(0, true, locationAlarmId, 200),
            AlarmDistanceDb(0, true, locationAlarmId2, 200)).toList()

        val alarmList2 = arrayOf(AlarmDistanceDb(0, true, locationAlarmId2, 200)).toList()

        repo.createOrUpdateAlarmsById(alarmList, locationAlarmId)
            .test()
            .assertNoErrors()

        repo.createOrUpdateAlarmsById(alarmList2, locationAlarmId2)
            .test()
            .assertNoErrors()

        repo.deleteAlarmDistancesByLocationAlarmId(locationAlarmId)
            .test()
            .assertNoErrors()

        assert(repo.findAll().size == 1)
        assert(repo.findAll().get(0).locationAlarmId == locationAlarmId2)
    }
}