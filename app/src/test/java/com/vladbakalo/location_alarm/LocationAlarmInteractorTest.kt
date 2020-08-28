package com.vladbakalo.location_alarm

import com.vladbakalo.location_alarm.data.repo.AlarmRepository
import com.vladbakalo.location_alarm.data.repo.LocationAlarmRepository
import com.vladbakalo.location_alarm.interactor.LocationAlarmInteractor
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
class LocationAlarmInteractorTest {
    val TAG = "LocationAlarmInteractorTest"
    @Mock
    lateinit var locationAlarmRepo: LocationAlarmRepository
    @Mock
    lateinit var alarmRepo: AlarmRepository

    lateinit var locationAlarmInteractor: LocationAlarmInteractor

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        locationAlarmInteractor = LocationAlarmInteractor(locationAlarmRepo, alarmRepo)
    }

    @After
    fun destroy(){
    }

    @Test
    fun testDeletion(){
        val locationAlarmId = 0L

        Mockito.`when`(alarmRepo.deleteAlarmByLocationAlarmId(locationAlarmId)).thenReturn(
            Completable.complete())
        Mockito.`when`(locationAlarmRepo.deleteLocationAlarm(locationAlarmId)).thenReturn(
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