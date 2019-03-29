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

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
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
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class OpenECACView {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openECACView() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.database_button), withText("Saved Resumes"),
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

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.style_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(4);
        appCompatCheckedTextView.perform(click());

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
                allOf(withId(android.R.id.title), withText("View"),
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

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.nameView), withText("John Doe"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.resumeview),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("John Doe")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.addressPhoneEmailView), withText("987 555 1234 | john.doe@example.com,"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.resumeview),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("987 555 1234 | john.doe@example.com,")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.experienceView1), withText("Groundskeeper, Google, 2018-01-2018-07"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.resumeview),
                                        0),
                                8),
                        isDisplayed()));
        textView5.check(matches(withText("Groundskeeper, Google, 2018-01-2018-07")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.experienceView2), withText("Painter, Microsoft, 2017-10-2017-12"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.resumeview),
                                        0),
                                9),
                        isDisplayed()));
        textView6.check(matches(withText("Painter, Microsoft, 2017-10-2017-12")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.academicExperienceView), withText("101 Ways to Blend In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.resumeview),
                                        0),
                                13),
                        isDisplayed()));
        textView7.check(matches(withText("101 Ways to Blend In")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.additionalInfoView), withText("Being average, watching grass grow, watching paint dry"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.resumeview),
                                        0),
                                18),
                        isDisplayed()));
        textView8.check(matches(withText("Being average, watching grass grow, watching paint dry")));

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
