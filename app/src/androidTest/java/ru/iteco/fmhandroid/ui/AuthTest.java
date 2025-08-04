package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AuthTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Description("Авторизация с валидными данными")
    @Test
    public void authValidTest() {
        ViewInteraction textInputEditText = onView(withId(R.id.login_text_input_layout));       //поиск элемента
        textInputEditText.check(matches(isDisplayed()));                                        //проверка на видимость
        textInputEditText.perform(replaceText("login2"), closeSoftKeyboard());      //замена текста и закрытие клавиатуры

        ViewInteraction textInputEditText2 = onView(withId(R.id.password_text_input_layout));   //поиск элемента
        textInputEditText2.check(matches(isDisplayed()));                                       //проверка на видимость
        textInputEditText2.perform(replaceText("password2"), closeSoftKeyboard());  //замена текста и закрытие клавиатуры

        ViewInteraction materialButton = onView(withId(R.id.enter_button));
        materialButton.check(matches(isDisplayed()));
        materialButton.perform(click());

        ViewInteraction linearLayout = onView(withId(R.id.container_list_news_include_on_fragment_main));
        linearLayout.check(matches(isDisplayed()));
    }

}
