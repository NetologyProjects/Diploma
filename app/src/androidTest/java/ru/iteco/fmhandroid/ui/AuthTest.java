package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.pageObject.Utils.waitDisplayed;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.R;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AuthTest {
    AuthorizationPage authorizationPage = new AuthorizationPage();
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    String validLogin = "login2";
    String validPassword = "password2";
    private View decorView;


    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<AppActivity>() {
            @Override
            public void perform(AppActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });


        onView(isRoot()).perform(waitDisplayed(appBar.getAppBarFragmentMain(), 15000));
        if (mainPage.isDisplayedButtonProfile()) {
            appBar.logOut(); //Выход из аккаунта
        }
        authorizationPage.visibilityAuth(); // Проверка видимости экрана 'Авторизация'
    }


    @Description("Авторизация с валидными данными")
    @Test
    public void successfulAuthorization() {
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.inputInFieldPassword(validPassword);
        authorizationPage.pressButton();
        mainPage.checkNews();
    }

    @Description("Авторизация с пустым логином")
    @Test
    public void authorizationWithEmptyLogin() throws InterruptedException {
        authorizationPage.inputInFieldPassword(validPassword);
        authorizationPage.pressButton();


        Thread.sleep(100);
        onView(withText(R.string.empty_login_or_password))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(withText(R.string.empty_login_or_password)))
                .check(matches(isDisplayed()))
        ;
    }

    @Description("Авторизация с пустым паролем")
    @Test
    public void authorizationWithEmptyPass() {
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();
    }

    @Description("Авторизация с пробелом вместо логина")
    @Test
    public void authorizationSpacesInLogin() throws InterruptedException {
        authorizationPage.inputInFieldLogin("y ");
        authorizationPage.inputInFieldPassword(validPassword);
        authorizationPage.pressButton();

        Thread.sleep(100);
        onView(withText(R.string.error))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(withText(R.string.error)))
                .check(matches(isDisplayed()))
        ;

    }

    @Description("Авторизация с пробелом вместо пароля")
    @Test
    public void authorizationSpacesInPass() {
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.inputInFieldPassword(" ");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();
    }

    @Description("Авторизация с верным логином в другом регистре")
    @Test
    public void authorizationWithLoginInDifferentRegister() {
        authorizationPage.inputInFieldLogin("LOGIN2");
        authorizationPage.inputInFieldPassword(validPassword);
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();

    }

    @Description("Авторизация с верным паролем в другом регистре")
    @Test
    public void authorizationWithPassInDifferentRegister() {
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.inputInFieldPassword("PASSWORD2");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();
    }

    @Description("Авторизация с невалидным логиом")
    @Test
    public void authorizationInvalidLogin() {
        authorizationPage.inputInFieldLogin("qwerty");
        authorizationPage.inputInFieldPassword(validPassword);
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();

    }

    @Description("Авторизация с невалидным паролем")
    @Test
    public void authorizationInvalidPass() {
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.inputInFieldPassword("qwerty");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();
    }


}