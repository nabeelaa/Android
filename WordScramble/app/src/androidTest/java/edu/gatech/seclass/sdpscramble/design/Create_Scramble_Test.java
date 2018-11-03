package edu.gatech.seclass.sdpscramble.design;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.gatech.seclass.sdpscramble.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Create_Scramble_Test {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void create_Scramble_Test() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.addPlayerBtn), withText("Add Player"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.username), isDisplayed()));
        textInputEditText.perform(replaceText("n"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.first_name), isDisplayed()));
        textInputEditText2.perform(replaceText("n"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.last_name), isDisplayed()));
        textInputEditText3.perform(replaceText("n"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.email), isDisplayed()));
        textInputEditText4.perform(replaceText("n@a"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.add_player_button), withText("Submit"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button3), withText("Login")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.create_scramble_button), withText("+ Create Scramble"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.input_phrase), isDisplayed()));
        textInputEditText5.perform(replaceText("this is a test phrase"), closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.input_hint), isDisplayed()));
        textInputEditText6.perform(replaceText("test phrase"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.scramblePhrase), withText("Scramble"),
                        withParent(withId(R.id.layout_4)),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.scramblePhrase), withText("Scramble"),
                        withParent(withId(R.id.layout_4)),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.save_scramble), withText("Save"),
                        withParent(withId(R.id.layout_5)),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(android.R.id.button3), withText("Back")));
        appCompatButton8.perform(scrollTo(), click());


        ViewInteraction appCompatTextView = onView(
                allOf(withText("Profile"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.logOutBtn), withText("Log Out"), isDisplayed()));
        appCompatButton9.perform(click());

    }

}
