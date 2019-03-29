package com.example.resyoume;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
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
public class OpenCLAView {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openCLAView() {
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
                .atPosition(3);
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
                allOf(withId(R.id.addressPhoneEmailView), withText("1600 Amphitheatre Parkway,Mountain View,CA 94043 • 987 555 1234 • john.doe@example.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.resumeview),
                                        0),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText("1600 Amphitheatre Parkway,Mountain View,CA 94043 • 987 555 1234 • john.doe@example.com")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.educationViewLeft1), withText("University of South Dakota"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        4),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("University of South Dakota")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.experienceViewLeft1), withText("Groundskeeper-Google,United States"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        7),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Groundskeeper-Google,United States")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.experienceViewLeft2), withText("Painter-Microsoft,United States"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        9),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("Painter-Microsoft,United States")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.leadershipViewLeft1),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        14),
                                0),
                        isDisplayed()));
        textView7.check(matches(withText("")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.leadershipView1), withText("101 Ways to Blend In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.resumeview),
                                        0),
                                15),
                        isDisplayed()));
        textView8.check(matches(withText("101 Ways to Blend In")));

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
