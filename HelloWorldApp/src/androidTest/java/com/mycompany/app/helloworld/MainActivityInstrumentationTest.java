package com.mycompany.app.helloworld;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.google.android.apps.common.testing.ui.espresso.matcher.BoundedMatcher;
import com.google.android.apps.common.testing.ui.espresso.matcher.PreferenceMatchers;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeTextIntoFocusedView;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withContentDescription;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;

import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.not;


public class MainActivityInstrumentationTest extends ActivityInstrumentationTestCase2<MainActivity> {


    public MainActivityInstrumentationTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getInstrumentation().getTargetContext());
        preferences.edit().clear().commit();

        getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @SuppressWarnings("unchecked")
    public void testMainActivityIsUsingDefaultGreeting() {

        onView(withContentDescription("greeting")).check(matches(withText("Hello World")));

    }

    @SuppressWarnings("unchecked")
    public void testChangingGreetingNamePreferenceWithCancelClickMeansMainActivityIsUsingDefaultGreeting() {

        onView(withContentDescription("greeting")).check(matches(withText("Hello World")));

        //onView(withContentDescription("More options")).perform(click());
        openActionBarOverflowOrOptionsMenu(this.getInstrumentation().getTargetContext());
        onView(withText(R.string.action_settings)).check(matches(isDisplayed()));
        onView(withText(R.string.action_settings)).perform(click());

        onData (withPreferenceKey ( "greeting_name" )).check(matches(isDisplayed()));
        onData (withPreferenceKey ( "greeting_name" )).perform(click());

        //onView(withId(android.R.id.edit)).perform(click());
        onView(withId(android.R.id.edit)).perform(clearText(), typeTextIntoFocusedView("James Brown"));
        onView(withId(android.R.id.edit)).perform(closeSoftKeyboard());
        onView(withText("Cancel")).perform(click());

        pressBack();

        onView(withContentDescription("greeting")).check(matches(withText("Hello John Smith")));

    }

    @SuppressWarnings("unchecked")
    public void testChangingGreetingNamePreferenceWithOkClickMeansMainActivityIsUsingNewGreeting() {

        onView(withContentDescription("greeting")).check(matches(withText("Hello World")));

        //onView(withContentDescription("More options")).perform(click());
        openActionBarOverflowOrOptionsMenu(this.getInstrumentation().getTargetContext());
        onView(withText(R.string.action_settings)).check(matches(isDisplayed()));
        onView(withText(R.string.action_settings)).perform(click());

        onData (withPreferenceKey ( "greeting_name" )).check(matches(isDisplayed()));
        onData (withPreferenceKey ( "greeting_name" )).perform(click());

        //onView(withId(android.R.id.edit)).perform(click());
        onView(withId(android.R.id.edit)).perform(clearText(), typeTextIntoFocusedView("James Brown"));
        onView(withId(android.R.id.edit)).perform(closeSoftKeyboard());
        onView(withText("OK")).perform(click());

        pressBack();

        onView(withContentDescription("greeting")).check(matches(withText("Hello James Brown")));

    }


    public  static Matcher<Object> withPreferenceKey (String expectedKeyText) {
        checkNotNull (expectedKeyText);
        checkArgument(!expectedKeyText.isEmpty());

        return  withPreferenceKey(PreferenceMatchers.withKey(expectedKeyText));
    }

    public  static  Matcher <Object> withPreferenceKey ( final  Matcher <Preference> preferenceMatcher) {

        checkNotNull (preferenceMatcher);

        return  new  BoundedMatcher <Object, Preference> (Preference.class ) {

            @Override
            public  void  describeTo (Description description) {
                description.appendText("with preference key:");
                preferenceMatcher.describeTo (description);
            }

            @Override
            protected  boolean  matchesSafely (Preference pref) {
                return  preferenceMatcher.matches(pref);
            }
        };
    }

}