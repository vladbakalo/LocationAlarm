package com.vladbakalo.location_alarm.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class LocalCiceroneHolder {
    private val containers: HashMap<String, Cicerone<Router>> = HashMap()

    fun getCicerone(containerTag: String): Cicerone<Router> {
        if (containers.containsKey(containerTag).not()) {
            containers[containerTag] = Cicerone.create()
        }
        return containers[containerTag]!!
    }
}