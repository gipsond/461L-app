package com.example.resyoume.EditTests;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.resyoume.MainActivity;
import com.example.resyoume.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EditTest {

    private boolean setUpIsDone = false;

    private void openDatabase(){

        ViewInteraction appCompatImageButton = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.database_button),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout1),
                                        childAtPosition(
                                                withId(R.id.linearLayout6),
                                                1)),
                                1)));
        appCompatImageButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openEdit(){
        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recyclerview),
                                childAtPosition(
                                        withId(R.id.include),
                                        0)),
                        1),
                        isDisplayed()));
        linearLayout.perform(longClick());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.title), withText("Edit")));
        textView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void TestChildAtPosition(int position, String testText){
        if(!setUpIsDone){
            openDatabase();
            setUpIsDone = true;
        }

        openEdit();

        ViewInteraction editText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.edit_linear_layout),
                                position),
                        1)));
        editText.perform(scrollTo(), click());

        ViewInteraction editText2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.edit_linear_layout),
                                position),
                        1)));
        editText2.perform(scrollTo(), replaceText(testText));

        ViewInteraction editText3 = onView(
                allOf(withText(testText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.edit_linear_layout),
                                        position),
                                1),
                        isDisplayed()));
        editText3.perform(closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withText("Save Resume"),
                        childAtPosition(
                                allOf(withId(R.id.edit_linear_layout),
                                        childAtPosition(
                                                withId(R.id.edit_scroll_view),
                                                0)),
                                36)));
        button.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        openEdit();

        editText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.edit_linear_layout),
                                position),
                        1)));
        editText.perform(scrollTo(), click());

        editText.perform(scrollTo());
        editText.check(matches(withText(testText)));
    }


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void editTitleTest() { TestChildAtPosition(1, "TitleTest");}

    @Test
    public void editFirstNameTest(){TestChildAtPosition(2, "FirstNameTest");}

    @Test
    public void editLastNameTest(){TestChildAtPosition(3, "LastNameTest");}

    @Test
    public void editEmailAddressTest(){TestChildAtPosition(4, "EmailAddressTest");}

    @Test
    public void editPhoneNumberTest(){TestChildAtPosition(5, "PhoneNumberTest");}

    @Test
    public void editAddressTest(){TestChildAtPosition(6, "AddressTest");}

    @Test
    public void editHomepageTest(){TestChildAtPosition(7, "HomepageTest");}

    @Test
    public void editCityTest(){TestChildAtPosition(8, "CityTest");}

    @Test
    public void editStateTest(){TestChildAtPosition(9, "StateTest");}

    @Test
    public void editPostalCodeTest(){TestChildAtPosition(10, "PostalCodeTest");}

    @Test
    public void editCountryTest(){TestChildAtPosition(11, "CountryTest");}

    @Test
    public void editInterestsTest(){TestChildAtPosition(12, "InterestsTest");}

    @Test
    public void editPublicationsTest(){TestChildAtPosition(13, "PublicationsTest");}

    @Test
    public void editEducationDateFromTest(){TestChildAtPosition(16, "EducationFromTest");}

    @Test
    public void editEducationDateToTest(){TestChildAtPosition(17, "EducationToTest");}

    @Test
    public void editEducationSchoolNameTest(){TestChildAtPosition(18, "EducationSchoolNameTest");}

    @Test
    public void editEducationCountryTest(){TestChildAtPosition(19, "EducationCountryTest");}

    @Test
    public void editEducationGraduationTest(){TestChildAtPosition(20, "EducationGraduationTest");}





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
