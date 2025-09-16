package ru.iteco.fmhandroid.ui.elements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import org.hamcrest.Matchers;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class Toast {

    @Step("Проверка тост-сообщения 'Логин и пароль не могут быть пустыми'")
    public void emptyLoginOrPassword(View decorView) {
        Allure.step("Проверка тост-сообщения 'Логин и пароль не могут быть пустыми'");
        onView(withText(R.string.empty_login_or_password))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(withText(R.string.empty_login_or_password)))
                .check(matches(isDisplayed()));
    }

    @Step("Проверка тост-сообщения 'Что-то пошло не так. Попробуйте позднее.'")
    public void error(View decorView) {
        Allure.step("Проверка тост-сообщения 'Что-то пошло не так. Попробуйте позднее.'");
        onView(withText(R.string.error))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(withText(R.string.error)))
                .check(matches(isDisplayed()));
    }
}
