package com.vladbakalo.location_alarm.ui.activity.main


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.ui.fragment.distance_alarm_list.adapter.DistanceAlarmAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule = GrantPermissionRule.grant("android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_BACKGROUND_LOCATION")

    @Test
    fun mainNavigationActivityOpenAlarmCreateScreenTest() {
        val bottomNavigationItemView = onView(
            allOf(withId(R.id.bottomNavigationMenuList), withContentDescription("List"),
                childAtPosition(childAtPosition(withId(R.id.mainActivityBnvContainer), 0), 1),
                isDisplayed()))
        bottomNavigationItemView.perform(click())

        val extendedFloatingActionButton = onView(
            allOf(withId(R.id.alarmListFabAddButton), withText("Add Alarm"), isDisplayed()))
        extendedFloatingActionButton.perform(click())

        onView(withId(R.id.locationAlarmCreateRoot))
            .check(matches(isDisplayed()))
    }

    @Test
    fun mainNavigationActivityOpenAlarmCreateScreenWithBackTest() {
        val bottomNavigationItemListView = onView(
            allOf(withId(R.id.bottomNavigationMenuList),
                withContentDescription("List"),
                childAtPosition(childAtPosition(withId(R.id.mainActivityBnvContainer), 0), 1),
                isDisplayed()))
        bottomNavigationItemListView.perform(click())

        val extendedFloatingActionButton = onView(
            allOf(withId(R.id.alarmListFabAddButton), withText("Add Alarm"), isDisplayed()))
        extendedFloatingActionButton.perform(click())

        onView(withId(R.id.locationAlarmCreateRoot))
            .check(matches(isDisplayed()))

        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.alarmListClRoot))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAlarmCreationWithCheckInList(){
        val bottomNavigationItemView = onView(
            allOf(withId(R.id.bottomNavigationMenuList), withContentDescription("List"),
                childAtPosition(childAtPosition(withId(R.id.mainActivityBnvContainer), 0), 1),
                isDisplayed()))
        bottomNavigationItemView.perform(click())

        val extendedFloatingActionButton = onView(
            allOf(withId(R.id.alarmListFabAddButton), withText("Add Alarm"),
                withContentDescription("Add Alarm"),
                isDisplayed()))
        extendedFloatingActionButton.perform(click())

        Thread.sleep(100)
        //Add Alarm Distance
        onView(withId(R.id.locationAlarmCreateBtnAddAlarm)).perform(click())

        Thread.sleep(500)
        onView(withId(R.id.locationAlarmCreateRvAlarmList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<DistanceAlarmAdapter.ItemVH>(0, click()))

        var alarmDistance = onView(allOf(withId(R.id.mainEditTextTilText),
            childAtPosition(childAtPosition(childAtPosition(withId(R.id.itemAlarmEtDistanceText), 0), 0), 0),
            isDisplayed()))
        alarmDistance.perform(typeText("1000"), ViewActions.closeSoftKeyboard())

        //Name
        onView(withId(R.id.locationAlarmCreateEtNameText)).perform(click())

        var alarmName = onView(allOf(withId(R.id.mainEditTextTilText),
            childAtPosition(childAtPosition(childAtPosition(withId(R.id.locationAlarmCreateEtNameText), 0), 0), 0),
            isDisplayed()))
        alarmName.perform(typeText("testName"), ViewActions.closeSoftKeyboard())

        //Address
        onView(withId(R.id.locationAlarmCreateEtAddressText)).perform(click())

        var alarmAddress = onView(allOf(withId(R.id.mainEditTextTilText),
            childAtPosition(childAtPosition(childAtPosition(withId(R.id.locationAlarmCreateEtAddressText), 0), 0), 0),
            isDisplayed()))
        alarmAddress.perform(typeText("testAddress"), ViewActions.closeSoftKeyboard())

        //Latitude
        onView(withId(R.id.locationAlarmCreateEtLatitudeText)).perform(click())

        var alarmLatitude = onView(allOf(withId(R.id.mainEditTextTilText),
            childAtPosition(childAtPosition(childAtPosition(withId(R.id.locationAlarmCreateEtLatitudeText), 0), 0), 0),
            isDisplayed()))
        alarmLatitude.perform(typeText("50"), ViewActions.closeSoftKeyboard())

        //Longitude
        onView(withId(R.id.locationAlarmCreateEtLongitudeText)).perform(click())

        var alarmLongitude = onView(allOf(withId(R.id.mainEditTextTilText),
            childAtPosition(childAtPosition(childAtPosition(withId(R.id.locationAlarmCreateEtLongitudeText), 0), 0), 0),
            isDisplayed()))
        alarmLongitude.perform(typeText("60"), ViewActions.closeSoftKeyboard())

        //Click Save
        onView(withId(R.id.menuCreateAlarmItemSave))
            .perform(click())

        Thread.sleep(500)
        //Check new item in list
        val list = onView(allOf(withId(R.id.alarmListClRoot)))
        var isDisplayed = true
        try {
            list.perform(click())
        } catch (e: PerformException){
            isDisplayed = false
        }

        assert(isDisplayed.not())
    }

    @Test
    fun mainNavigationActivityTabsVisibilityOnOpenedKeyboardSelect(){
        val bottomNavigationItemView = onView(
            allOf(withId(R.id.bottomNavigationMenuList),
                withContentDescription("List"),
                childAtPosition(childAtPosition(withId(R.id.mainActivityBnvContainer), 0), 1),
                isDisplayed()))
        bottomNavigationItemView.perform(click())

        val extendedFloatingActionButton = onView(
            allOf(withId(R.id.alarmListFabAddButton), withText("Add Alarm"), isDisplayed()))
        extendedFloatingActionButton.perform(click())

        onView(withId(R.id.locationAlarmCreateRoot))
            .check(matches(isDisplayed()))

        Thread.sleep(500)

        val nameField = onView(withId(R.id.locationAlarmCreateEtNameText))
        nameField.perform(click())

        Thread.sleep(500)

        val bottomNavigationItemListView = onView(
            allOf(withId(R.id.bottomNavigationMenuSettings),
                withContentDescription("Settings"),
                childAtPosition(childAtPosition(withId(R.id.mainActivityBnvContainer), 0), 2),
                isDisplayed()))
        var isDisplayed = true
        try {
            bottomNavigationItemListView.perform(click())
        } catch (e: PerformException){
            isDisplayed = false
        }

        assert(isDisplayed.not())
    }

    fun isKeyboardShown(): Boolean {
        val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(
            Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isAcceptingText
    }

    private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object :TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(
                    parent) && view == parent.getChildAt(position)
            }
        }
    }
}
