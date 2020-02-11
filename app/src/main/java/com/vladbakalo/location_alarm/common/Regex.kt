package com.vladbakalo.location_alarm.common

object Regex {
    private const val LATITUDE =
        "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:[\\.]?(?:\\.[0-9]{1,6})?))\$"
    private const val LONGITUDE =
        "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:[\\.]?(?:\\.[0-9]{1,6})?))\$"
    private const val INTEGER = "[+]?[0-9]+"
}