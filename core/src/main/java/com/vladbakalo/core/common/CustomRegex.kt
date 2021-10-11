package com.vladbakalo.core.common

object CustomRegex {
    const val LATITUDE =
        "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:[\\.]?(?:\\.[0-9]{1,6})?))\$"
    const val LONGITUDE =
        "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:[\\.]?(?:\\.[0-9]{1,6})?))\$"
    const val INTEGER = "[+]?[0-9]+"
}