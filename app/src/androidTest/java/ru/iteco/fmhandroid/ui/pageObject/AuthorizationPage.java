package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Utils;

public class AuthorizationPage {
    MainPage mainPage = new MainPage();
    AppBarPage appBarPage = new AppBarPage();

    String validLogin = "login2";
    String validPassword = "password2";
    int enterButton = R.id.enter_button;
    public ViewInteraction buttonSingIn = onView(withId(enterButton));

    @Step("Ввод в поле Логин")
    public void inputInFieldLogin(String login) {
        Allure.step("Ввод в поле Логин");
        ViewInteraction inputInFieldLogin = onView(withHint("Логин"));
        // Заменяем предыдущее содержимое поля и вводим новые данные
        inputInFieldLogin.perform(replaceText(login));
        closeSoftKeyboard();
    }

    @Step("Ввод в поле Пароль")
    public void inputInFieldPassword(String password) {
        Allure.step("Ввод в поле Пароль");
        ViewInteraction inputInFieldPassword = onView(withHint("Пароль"));
        // Заменяем предыдущее содержимое поля и вводим новые данные
        inputInFieldPassword.perform(replaceText(password), closeSoftKeyboard());
        // Подтверждение ввода
        pressImeActionButton();
        // Возврат
        pressBack();
    }

    @Step("Нажатие на кнопку входа")
    public void pressButton() {
        Allure.step("Нажатие на кнопку входа");
        buttonSingIn.check(matches(isDisplayed()));
        buttonSingIn.perform(click());
    }

    @Step("Видимость экрана 'Авторизация'")
    public void visibilityAuth() {
        Allure.step("Проверка видимости экрана 'Авторизация'");
        ViewInteraction textViewAuth = onView(withText("Авторизация"));
        textViewAuth.check(matches(isDisplayed()));
        textViewAuth.check(matches(withText("Авторизация")));
    }

    @Step("Успешная авторизация пользователя")
    public void successfulAuthorization() {
        Allure.step("Успешная авторизация пользователя");
        onView(isRoot()).perform(Utils.waitDisplayed(enterButton, 5000));
        inputInFieldLogin(validLogin);
        inputInFieldPassword(validPassword);
        pressButton();
        onView(isRoot()).perform(Utils.waitDisplayed(appBarPage.getPressProfile(), 5000));
        mainPage.checkNews();
    }
}
