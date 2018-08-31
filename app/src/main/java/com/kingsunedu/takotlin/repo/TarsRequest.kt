package com.kingsunedu.takotlin.repo

import com.kingsunsoft.sdk.login.net.request.base.Request
import com.kingsunsoft.sdk.mod.Header
import io.reactivex.Maybe
import java.lang.reflect.Type

abstract class TarsRequest<out REQUEST, out RESPONSE>(private val rspType: Type, private val request: REQUEST, p: Array<Any>) : Request() {
//    fun sendRequest: Maybe<RESPONSE> {
//        setHeader(makeHeader())
//        setBody(makeReqBinary())
//    }
//
//    override fun makeHeader(): Header {
//        val sHeader = super.makeHeader()
//        header.appRequestName = requestName<REQUEST>()
//        header.requestName = ""
//        return header
//    }

    private inline fun <reified REQUEST> requestName() = REQUEST::class.java.simpleName
}