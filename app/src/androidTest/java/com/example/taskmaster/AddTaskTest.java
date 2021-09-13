package com.example.taskmaster;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AddTaskTest {
    @Rule
    public ActivityScenarioRule<MainActivity>main =
            new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void addTaskTest(){
        onView(withId(R.id.but)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.taskTitleView)).check(matches(isDisplayed())).perform(typeText("Espresso Task"));
        onView(withId(R.id.taskDescriptionView)).check(matches(isDisplayed())).perform(typeText("Espresso Description"));
        onView(withId(R.id.taskStateView)).check(matches(isDisplayed())).perform(typeText("Espresso State"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button)).check(matches(isDisplayed())).perform(click());
        onView(withText("Espresso Task")).check(matches(isDisplayed()));

    }
}
