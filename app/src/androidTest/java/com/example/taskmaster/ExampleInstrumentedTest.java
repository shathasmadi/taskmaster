package com.example.taskmaster;

import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.support.test.espresso.action.ViewActions;

import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public static final String STRING_TO_BE_TYPED = "Espresso";
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.taskmaster", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<AllTask> All =
            new ActivityScenarioRule<>(AllTask.class);
    @Test
    public void headingIsVisible() {
        onView(withText("All Tasks")).check(matches(isDisplayed()));
    }

    @Rule
    public ActivityScenarioRule<MainActivity> main =
            new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void testMainActivityButtons() {
        onView(withId(R.id.settingButton)).perform(click());


    }


   }

