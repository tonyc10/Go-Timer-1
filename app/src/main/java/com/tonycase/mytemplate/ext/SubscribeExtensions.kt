package com.tonycase.mytemplate.ext

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/* Subscribe the onNext and optional onComplete, and this adds an onError { Timber::e } for you.*/
fun <T> Observable<T>.subscribeLogError(onNext: (T) -> Unit, onComplete: () -> Unit = {}) = subscribe(onNext, Timber::e, onComplete)
fun <T> Flowable<T>.subcribeLogError(onNext: (T) -> Unit, onComplete: () -> Unit = {}) = subscribe(onNext, Timber::e, onComplete)
fun <T> Maybe<T>.subscribeLogError(onSuccess: (T) -> Unit, onComplete: () -> Unit = {}) = subscribe(onSuccess, Timber::e, onComplete)

fun Completable.consume(onComplete: () -> Unit)                           = subscribe(onComplete, Timber::e)
fun <T> Single<T>.consume(onSuccess: (T) -> Unit)                              = subscribe(onSuccess, Timber::e)

fun <T> Observable<T>.consume(onNext: (T) -> Unit) = subscribeLogError(onNext)
fun <T>     Maybe<T>.consume(onSuccess: (T) -> Unit) = subscribeLogError(onSuccess)

/* Subscribe with no handler and this adds handlers for your which just log.*/
fun <T>   Observable<T>.subscribeLog() = subscribeLogError({ Timber.d("onNext: $it") })
fun <T> Flowable<T>.subscribeJustLog() = subcribeLogError( { Timber.d("onNext: $it") }, { Timber.d("onComplete")})
fun     Completable.subscribeJustLog() = consume( { Timber.d("onComplete")} )
fun <T>   Single<T>.subscribeJustLog() = consume({ Timber.d("onNext: $it") })
fun <T>    Maybe<T>.subscribeJustLog() = subscribeLogError({ Timber.d("onNext: $it") }, { Timber.d("onComplete") })

/* Subscribe with just strings as handlers for each condition e.g. onNext | onError, and the strings will be logged appropriately.*/
fun <T> Observable<T>.subscribeLogMsgs(onNextMsg: String, onErrorMsg: String) = subscribe({ Timber.d("$onNextMsg: $it") }, { Timber.e(it, onErrorMsg)})
fun <T>   Flowable<T>.subscribeLogMsgs(onNextMsg: String, onErrorMsg: String) = subscribe({ Timber.d("$onNextMsg: $it") }, { Timber.e(it, onErrorMsg)})
fun   Completable.subscribeLogMsgs(onCompleteMsg: String, onErrorMsg: String) = subscribe({ Timber.d("$onCompleteMsg") }, { Timber.e(it, onErrorMsg)})
fun <T>  Single<T>.subscribeLogMsgs(onSuccessMsg: String, onErrorMsg: String) = subscribe({ Timber.d("$onSuccessMsg: $it") }, { Timber.e(it, onErrorMsg)})
fun <T> Maybe<T>.subscribeLogMsgs(onSuccessMsg: String, onErrorMsg: String, onCompleteMsg: String) = subscribe({ Timber.d("$onSuccessMsg: $it") }, { Timber.e(it, onErrorMsg)}, { Timber.d("$onCompleteMsg") })

fun <T>  Observable<T>.observeOnMain() = observeOn(AndroidSchedulers.mainThread())
fun <T>  Single<T>.observeOnMain() = observeOn(AndroidSchedulers.mainThread())
fun <T>  Maybe<T>.observeOnMain() = observeOn(AndroidSchedulers.mainThread())
fun      Completable.observeOnMain() = observeOn(AndroidSchedulers.mainThread())

fun <T>  Single<T>.subscribeOnIo() = subscribeOn(Schedulers.io())
