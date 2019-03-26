package com.example.resyoume;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Style1ToNFC {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void style1ToNFC() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.database_button), withText("Database"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recyclerview),
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        linearLayout.perform(longClick());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.title), withText("Send to NFC Activity"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        textView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText = onView(
                allOf(withId(R.id.SendText), withText("[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"title\":\"Mr.\",\"address\":\"1600 Amphitheatre Parkway\",\"postcode\":\"94043\",\"city\":\"Mountain View\",\"state\":\"CA\",\"country\":\"United States\",\"email\":\"john.doe@example.com\",\"phoneNumber\":\"987 555 1234\",\"homepage\":\"linkedin.com\\/john-example-doe\",\"interests\":\"Being average, watching grass grow, watching paint dry\",\"publications\":\"101 Ways to Blend In\",\"plaintext\":\"This user was manually input and thus has no accompanying plaintext.\"},[{\"id\":1,\"contactId\":1,\"dateFrom\":\"2013-08\",\"dateTo\":\"2017-06\",\"schoolName\":\"University of South Dakota\",\"country\":\"United States\",\"plaintext\":\"This phase was manually input and thus has no accompanying plaintext.\"}],[{\"id\":1,\"contactId\":1,\"dateFrom\":\"2018-01\",\"dateTo\":\"2018-07\",\"function\":\"Groundskeeper\",\"company\":\"Google\",\"country\":\"United States\",\"plaintext\":\"This phase was manually input and thus has no accompanying plaintext.\"},{\"id\":2,\"contactId\":1,\"dateFrom\":\"2017-10\",\"dateTo\":\"2017-12\",\"function\":\"Painter\",\"company\":\"Microsoft\",\"country\":\"United States\",\"plaintext\":\"This phase (2) was manually input and thus has no accompanying plaintext.\"}]]"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        editText.check(matches(withText("[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"title\":\"Mr.\",\"address\":\"1600 Amphitheatre Parkway\",\"postcode\":\"94043\",\"city\":\"Mountain View\",\"state\":\"CA\",\"country\":\"United States\",\"email\":\"john.doe@example.com\",\"phoneNumber\":\"987 555 1234\",\"homepage\":\"linkedin.com\\/john-example-doe\",\"interests\":\"Being average, watching grass grow, watching paint dry\",\"publications\":\"101 Ways to Blend In\",\"plaintext\":\"This user was manually input and thus has no accompanying plaintext.\"},[{\"id\":1,\"contactId\":1,\"dateFrom\":\"2013-08\",\"dateTo\":\"2017-06\",\"schoolName\":\"University of South Dakota\",\"country\":\"United States\",\"plaintext\":\"This phase was manually input and thus has no accompanying plaintext.\"}],[{\"id\":1,\"contactId\":1,\"dateFrom\":\"2018-01\",\"dateTo\":\"2018-07\",\"function\":\"Groundskeeper\",\"company\":\"Google\",\"country\":\"United States\",\"plaintext\":\"This phase was manually input and thus has no accompanying plaintext.\"},{\"id\":2,\"contactId\":1,\"dateFrom\":\"2017-10\",\"dateTo\":\"2017-12\",\"function\":\"Painter\",\"company\":\"Microsoft\",\"country\":\"United States\",\"plaintext\":\"This phase (2) was manually input and thus has no accompanying plaintext.\"}]]")));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.SendNFC), withText("Send"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.NFCStatus), withText("Callback set. Looking for other device..."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Callback set. Looking for other device...")));

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
