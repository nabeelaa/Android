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
public class Solve_Scramble_Test {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void solve_Scramble_Test() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.addPlayerBtn), withText("Add Player"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.username), isDisplayed()));
        textInputEditText.perform(replaceText("n"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.first_name), isDisplayed()));
        textInputEditText2.perform(replaceText("nabeela"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.last_name), isDisplayed()));
        textInputEditText3.perform(replaceText("abidi"), closeSoftKeyboard());

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
        textInputEditText5.perform(replaceText("t"), closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.input_hint), isDisplayed()));
        textInputEditText6.perform(replaceText("tst"), closeSoftKeyboard());

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

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.addPlayerBtn), withText("Add Player"), isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.username), isDisplayed()));
        textInputEditText7.perform(replaceText("w"), closeSoftKeyboard());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.first_name), isDisplayed()));
        textInputEditText8.perform(replaceText("wuping"), closeSoftKeyboard());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.last_name), isDisplayed()));
        textInputEditText9.perform(replaceText("liu"), closeSoftKeyboard());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.email), isDisplayed()));
        textInputEditText10.perform(replaceText("w@y"), closeSoftKeyboard());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.add_player_button), withText("Submit"), isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(android.R.id.button3), withText("Login")));
        appCompatButton12.perform(scrollTo(), click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withText("t")));
        appCompatTextView2.perform(scrollTo(), click());

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.solution_input), isDisplayed()));
        textInputEditText11.perform(replaceText("t"), closeSoftKeyboard());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.submit_solution), withText("Submit"), isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(android.R.id.button3), withText("OK")));
        appCompatButton14.perform(scrollTo(), click());

        ViewInteraction appCompatTextView15 = onView(
                allOf(withText("Profile"), isDisplayed()));
        appCompatTextView15.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(R.id.logOutBtn), withText("Log Out"), isDisplayed()));
        appCompatButton16.perform(click());

    }

}
