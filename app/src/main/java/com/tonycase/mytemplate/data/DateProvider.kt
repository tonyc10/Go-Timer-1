package com.tonycase.mytemplate.data

import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A pretend class just to lay out archictecture in this Template.
 *
 * @author Tony Case (case.tony@gmail.com)
 * Created on 11/10/20.
 */

class DateProvider(
    private val dateToUse: Date = Date()
) {

    private val cal: Calendar = GregorianCalendar()

    init {
        cal.time = dateToUse
    }

    /** starts at 0 */
    fun month(): Single<Int> {
        return Single.timer(1, TimeUnit.SECONDS)
            .map { cal.get(Calendar.MONTH) }
    }

    /** starts at 1 */
    fun dayOfMonth(): Single<Int> {
        return Single.timer(2, TimeUnit.SECONDS)
            .doOnSubscribe { Timber.i("TC subscribed!") }
            .doOnSuccess { Timber.i("TC dayOfMonth timer success!") }
            .map { cal.get(Calendar.DAY_OF_MONTH) }
            .doOnSuccess { Timber.i("TC dayOfMonth success: $it") }

    }

    /** Sunday is 1, Saturday is 7 */
    fun dayOfWeek(): Single<Int> {
        return Single.timer(1, TimeUnit.SECONDS)
            .map { cal.get(Calendar.DAY_OF_WEEK) }
    }

    fun year(): Single<Int> {
        return Single.timer(3, TimeUnit.SECONDS)
            .map { cal.get(Calendar.YEAR) }
    }
}
