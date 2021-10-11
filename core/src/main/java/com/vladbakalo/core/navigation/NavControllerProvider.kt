package com.vladbakalo.core.navigation

import androidx.navigation.NavController

interface NavControllerProvider {

    fun getNavController(): NavController?
}