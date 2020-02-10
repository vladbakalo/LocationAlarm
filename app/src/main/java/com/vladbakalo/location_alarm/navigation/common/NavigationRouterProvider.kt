package com.vladbakalo.location_alarm.navigation.common

import ru.terrakok.cicerone.Router

interface NavigationRouterProvider {
    fun getRouter(): Router
}