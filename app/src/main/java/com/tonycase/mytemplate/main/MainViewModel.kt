package com.tonycase.mytemplate.main

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.tonycase.mytemplate.R
import com.tonycase.mytemplate.data.DateProvider
import com.tonycase.mytemplate.miscutilview.DialogSpec
import com.tonycase.mytemplate.miscutilview.SnackbarColor
import com.tonycase.mytemplate.miscutilview.SnackbarSpec
import com.tonycase.mytemplate.preferences.Prefs
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import java.lang.Exception
import java.lang.IllegalStateException

class MainViewModel @ViewModelInject constructor(
    private val dateProvider: DateProvider,
    private val prefs: Prefs,
    private val resources: Resources
) : ViewModel() {

    val dateState: BehaviorSubject<DateViewState> = BehaviorSubject.create()
    val loadingState: BehaviorSubject<Boolean> = BehaviorSubject.create()
    val dialogActions: PublishSubject<DialogAction> = PublishSubject.create()

    private lateinit var disposables: CompositeDisposable           // All of our Rx subscriptions, for easy management.

    fun load() {
        disposables = CompositeDisposable()

        dateState.onNext(DateViewState())
        loadingState.onNext(true)

        connectToRepos()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }


    private fun connectToRepos() {

        disposables.addAll(
            dateProvider.dayOfMonth()
                .map { it.toString() }
                .subscribe(
                    { dayStr ->
                        Timber.i("TC got day of month: $dayStr")
                        dateState.onNext(
                            currentDateState().copy(dayStr = dayStr)
                        )
                        loadingState.onNext(false)
                    },
                    { error -> errorDialog(error, "Error getting day of month") }  // dialog example
                ),
            dateProvider.dayOfWeek()
                .map { it.asDayOfWeek() }
                .subscribe(
                    { dayStr ->
                        dateState.onNext(
                            currentDateState().copy(dayOfWeekStr = dayStr)
                        )
                        loadingState.onNext(false)
                    },
                    { error ->
                        errorSnackbar(
                            error,
                            "Error getting day of week"
                        )
                    } //snackbar example
                ),
            dateProvider.month()
                .map { it.asMonth() }
                .subscribe(
                    { monthStr ->
                        dateState.onNext(
                            currentDateState().copy(monthStr = monthStr)
                        )
                        loadingState.onNext(false)
                    },
                    { error -> errorMsgOnly(error, "Error getting day of week") } //log only example
                ),
            dateProvider.year()
                .map { it.toString() }
                .subscribe(
                    { yearStr ->
                        dateState.onNext(
                            currentDateState().copy(yearStr = yearStr)
                        )
                        loadingState.onNext(false)
                    },
                    { error -> errorMsgOnly(error, "Error getting year") }        //log only example
                ),
        )
    }


    private fun errorMsgOnly(t: Throwable, msg: String) {
        Timber.e(t, msg)
        loadingState.onNext(false)
    }

    private fun errorDialog(t: Throwable, msg: String) {
        Timber.e(t, msg)

        @Suppress("DEPRECATION")
        val dialog = DialogSpec(title = resources.getString(R.string.error_title),
            message = msg + ", " + t.localizedMessage,
            icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_cancel_red_24dp, null)!! )

        dialogActions.onNext(DialogAction(dialogSpec = dialog))
        loadingState.onNext(false)
    }

    private fun errorSnackbar(t: Throwable, msg: String) {
        Timber.e(t, msg)

        val snackbarSpec = SnackbarSpec(
            text = msg,
            color = SnackbarColor.RED_BAD,
            duration = Snackbar.LENGTH_INDEFINITE)

        dialogActions.onNext(DialogAction(snackbarSpec = snackbarSpec))
        loadingState.onNext(false)
    }

    private fun Int.asDayOfWeek(): String {
        return when (this) {
            1 -> "Sunday"
            2 -> "Monday"
            3 -> "Tuesday"
            4 -> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            7 -> "Saturday"
            else -> throw IllegalStateException("Only integers 1-7 can be day of week strings")
        }
    }

    private fun Int.asMonth(): String {
        return when (this) {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            11 -> "December"
            else -> throw IllegalStateException("Only integers 0-11 can be month strings")
        }
    }

    private fun currentDateState() = dateState.value!!
}

data class DialogAction(
    val dialogSpec: DialogSpec? = null,                          // specs for a dialog to be displayed.
    val snackbarSpec: SnackbarSpec? = null
)

data class DateViewState(
    val dayOfWeekStr: String = "",
    val monthStr: String = "",
    val dayStr: String = "",
    val yearStr: String = ""
)
