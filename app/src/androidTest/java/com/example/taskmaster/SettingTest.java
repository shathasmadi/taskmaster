package com.example.taskmaster;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.taskmaster.ExampleInstrumentedTest.STRING_TO_BE_TYPED;

@RunWith(AndroidJUnit4.class)
public class SettingTest {
    @Rule
    public ActivityScenarioRule<Setting> main
            = new ActivityScenarioRule<>(Setting.class);
    @Test
    public void changeText_newActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editText)).perform(typeText(STRING_TO_BE_TYPED),
                closeSoftKeyboard());
        onView(withId(R.id.saveButton)).perform(click());

        onView(withId(R.id.view)).check(matches(withText(STRING_TO_BE_TYPED)));
    }
}
