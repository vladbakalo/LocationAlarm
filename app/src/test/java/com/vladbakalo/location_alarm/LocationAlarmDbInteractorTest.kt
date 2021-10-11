package com.vladbakalo.location_alarm

import com.vladbakalo.core.db.repo.AlarmDistanceRepository
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import com.vladbakalo.core.interactor.LocationAlarmInteractor
import io.reactivex.Completable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class LocationAlarmDbInteractorTest {
    val TAG = "LocationAlarmInteractorTest"
    @Mock
    lateinit var locationAlarmRepo: LocationAlarmRepository
    @Mock
    lateinit var alarmDistanceRepo: AlarmDistanceRepository

    lateinit var locationAlarmInteractor: LocationAlarmInteractor

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        locationAlarmInteractor = LocationAlarmInteractor(locationAlarmRepo, alarmDistanceRepo)
    }

    @After
    fun destroy(){
    }

    @Test
    fun testDeletion(){
        val locationAlarmId = 0L

        Mockito.`when`(alarmDistanceRepo.deleteAlarmDistancesByLocationAlarmId(locationAlarmId)).thenReturn(
            Completable.complete())
        Mockito.`when`(locationAlarmRepo.deleteLocationAlarmById(locationAlarmId)).thenReturn(
            Completable.complete())


        locationAlarmInteractor.deleteLocationAlarm(locationAlarmId)
            .test()
            .assertNoErrors()
    }

    fun log(e: Throwable){
        log(e.toString())
    }

    fun log(msg: String){
        println("$TAG : $msg")
    }
}