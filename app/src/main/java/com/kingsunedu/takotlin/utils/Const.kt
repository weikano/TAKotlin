package com.kingsunedu.takotlin.utils

import android.graphics.Color

internal class Const {

    companion object {
        final val COLOR_RIGHT = Color.parseColor("#0EB568")
        final val COLOR_WRONG = Color.parseColor("#FF5050")
        final val DOT = "."
        final val APP_ERRCODE_USERNOTFOUND = 0x401
        final val APP_ERRCODE_SECRETKEYMISS = 0x405

        final val CACHE_LOGIN_USER = "userIdentity"
        final val CACHE_LAST_lOGIN_PHONENUMBER = "lastLoginPhone"
        final val CACHE_TAPREADRECORD = "tapReading"


    }
}