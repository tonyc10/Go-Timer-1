package com.tonycase.mytemplate.main

import android.content.res.Resources
import com.google.common.truth.Truth.assertThat
import com.tonycase.mytemplate.R
import com.tonycase.mytemplate.data.DateProvider
import com.tonycase.mytemplate.preferences.Prefs
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * A simple starter unit test, for an Rx-based view model.  This will break of course, when
 * MainViewModel is changed.
 *
 * @author Tony Case (case.tony@gmail.com)
 * Created on 11/11/20.
 */

class MainViewModelTest {

    lateinit var resources: Resources
    lateinit var prefs: Prefs

    lateinit var date: Date

    @Before
    fun init() {
        resources = mockResources()
        prefs = mockk()        // don't need any yet.

    }

    @Test
    fun `get date returns proper date strings`() {

        date = Date(1605130446337L)  // today, Wed Nov. 11 2020

        val mainViewModel = MainViewModel(
            mockDateProvider(date),
            prefs,
            resources
        )

        mainViewModel.load()

        mainViewModel.dateState
            .test()
            .assertValueCount(1)
            .assertValue { viewState ->
                viewState.dayOfWeekStr == "Wednesday"
                        && viewState.dayStr == "11"
                        && viewState.monthStr == "November"
                        && viewState.yearStr == "2020"
            }
    }

    @Test
    fun `use a Truth API`() {
        assertThat(0.123).isWithin(.01).of(0.122)
    }

    private fun mockDateProvider(date: Date): DateProvider {
        // our repo does not depend on any external data, so is easy to mock here.
        val cal = GregorianCalendar()
        cal.time = date

        val dateProvider = mockk<DateProvider>() {   // Wed Nov. 11 2020
            every { month() } returns Single.just(cal.get(Calendar.MONTH))
            every { dayOfWeek() } returns Single.just(cal.get(Calendar.DAY_OF_WEEK))
            every { dayOfMonth() } returns Single.just(cal.get(Calendar.DAY_OF_MONTH))
            every { year() } returns Single.just(cal.get(Calendar.YEAR))
        }
        return dateProvider
    }

    private fun mockResources(): Resources {
        val resources = mockk<Resources>() {
            every { getString(R.string.error_title) } returns "Error"
        }
        // as an example. We won't hit
        return resources
    }
}

