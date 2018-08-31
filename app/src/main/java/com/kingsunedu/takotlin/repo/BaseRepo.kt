package com.kingsunedu.takotlin.repo

import com.blankj.utilcode.util.CacheUtils
import com.blankj.utilcode.util.ObjectUtils
import com.kingsunedu.takotlin.utils.Const
import com.kingsunsoft.sdk.login.net.request.business.TarsRequest
import com.kingsunsoft.sdk.login.net.utils.TarsUtils
import com.nytimes.android.external.store3.base.Persister
import com.nytimes.android.external.store3.base.RecordProvider
import com.nytimes.android.external.store3.base.RecordState
import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import com.qq.tars.protocol.tars.TarsOutputStream
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * [RESPONSE] Tars请求的返回值
 *
 * [TRANSFER] 从[RESPONSE] 转换后的类型
 */
abstract class BaseRepo<RESPONSE,TRANSFER>(val type: Class<RESPONSE>) {
    var repository : Store<TRANSFER, String>
    init {
        repository = StoreBuilder.parsedWithKey<String, RESPONSE, TRANSFER>()
                .fetcher {
                    fetchNetworkApi(params).doOnError{th -> throwable = th}
                }.persister(DefaultPersister())
                .parser(this::transfer)
                .networkBeforeStale()
                .open()
    }
    private var params = arrayOf<Any>()
    lateinit var throwable: Throwable

    abstract fun fetchNetworkApi(vararg params:Any): Single<RESPONSE>

    private fun read(cache :ByteArray) : RESPONSE {
        return (TarsUtils.getBodyRsp(type, cache) as RESPONSE)
    }

    abstract fun transfer(response : RESPONSE) : TRANSFER

    private fun write(key : String, raw : RESPONSE) : Boolean {
        val os = TarsOutputStream()
        val writeTo = type.getMethod("writeTo", TarsOutputStream::class.java)
        writeTo.invoke(raw, os)
        CacheUtils.getInstance().put(key, os.toByteArray())
        return true
    }

    private fun defaultArgs() = Single.just(params)

    fun fetch(p : Array<Any>) : Single<TRANSFER> {
        return defaultArgs().flatMap { repository.fetch(getStoreKey(p)) }
    }

    fun get(p : Array<Any>) : Single<TRANSFER> {
        return defaultArgs().flatMap { repository.get(getStoreKey(p)) }
    }

    fun clearCache() {
        repository.clear()
    }

    fun cacheAndFetch(p: Array<Any>): Observable<TRANSFER> {
        val key = getStoreKey(p)
        val fetch = repository.fetch(key).toObservable()
        return repository.getWithResult(key).toObservable().flatMap { if(it.isFromCache) {
                Observable.concat(Observable.just(it.value()), fetch)
            }else {
                Observable.just(it.value())
            }
        }
    }

    private fun getStoreKey(p: Array<out Any>): String {
        val builder = StringBuilder()
        builder.append(this::class.java.simpleName).append("|")
        for (item in p) {
            if(ObjectUtils.isNotEmpty(item)) {
                builder.append(System.identityHashCode(item))
            }
        }
        builder.append("|")
        val userId = CacheUtils.getInstance().getString(Const.CACHE_LOGIN_USER)
        val identity = if(ObjectUtils.isNotEmpty(userId)) {
            ""
        }else {
            userId
        }
        return if(isCommonData()) {
            builder.toString()
        }else {
            builder.append(identity).toString()
        }
    }

    fun isCommonData() : Boolean = false

    private inner class DefaultPersister : Persister<RESPONSE, String>, RecordProvider<String> {
        override fun getRecordState(key: String) = RecordState.STALE

        override fun read(key: String): Maybe<RESPONSE> {
            val cache = CacheUtils.getInstance().getBytes(key)
            if(ObjectUtils.isNotEmpty(throwable)){
                if(cache == null || cache.isEmpty()) {
                    return Maybe.error(throwable)
                }
            }
            return Maybe.just(read(cache))
        }

        override fun write(key: String, raw: RESPONSE): Single<Boolean> {
            return Single.just(this@BaseRepo.write(key, raw))
        }

    }
}

