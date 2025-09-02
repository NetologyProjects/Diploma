package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import io.qameta.allure.kotlin.Step;

public class WarningError {
    @Step("Проверка видимости сообщения 'Неверный логин или пароль'")
    public void windowError() {
        onView(withText("Что-то пошло не так. Попробуйте позднее."))
                .check(matches(isDisplayed()));
        //onView(isRoot()).perform(waitDisplayed(withText("Что-то пошло не так. Попробуйте позднее."), 5000)); //проверка с ожиданием 5 сек.
    }

    @Step("Проверка видимости сообщения 'Логин и пароль не могут быть пустыми'")
    public void windowEmptyInputField() {
        onView(withText("Логин и пароль не могут быть пустыми"))
                .check(matches(isDisplayed()));
    }
}
