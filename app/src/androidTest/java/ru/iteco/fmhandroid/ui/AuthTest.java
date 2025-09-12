package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static ru.iteco.fmhandroid.ui.pageObject.Utils.waitDisplayed;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.ui.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AuthTest {
    AuthorizationPage authorizationPage = new AuthorizationPage();
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();


    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);


    @Before
    public void setUp() {
        Espresso.onView(isRoot()).perform(waitDisplayed(appBar.getAppBarFragmentMain(), 15000));
        if (mainPage.isDisplayedButtonProfile()) {
            appBar.logOut();
        }
    }

    @Description("Авторизация с валидными данными")
    @Test
    public void successfulAuthorization() {
        authorizationPage.visibilityAuth();
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        mainPage.checkNews();
    }

    @Description("Авторизация с пустым логином")
    @Test
    public void authorizationWithEmptyLogin() {
        authorizationPage.visibilityAuth();
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();
    }

    @Description("Авторизация с пустым паролем")
    @Test
    public void authorizationWithEmptyPass() {
        authorizationPage.visibilityAuth();
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();
    }

    @Description("Авторизация с пробелом вместо логина")
    @Test
    public void authorizationSpacesInLogin() {
        authorizationPage.visibilityAuth();
        authorizationPage.inputInFieldLogin(" ");
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();

    }

    @Description("Авторизация с пробелом вместо пароля")
    @Test
    public void authorizationSpacesInPass() {
        authorizationPage.visibilityAuth();
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.inputInFieldPassword(" ");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();
    }

    @Description("Авторизация с верным логином в другом регистре")
    @Test
    public void authorizationWithLoginInDifferentRegister() {
        authorizationPage.visibilityAuth();
        authorizationPage.inputInFieldLogin("LOGIN2");
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();

    }

    @Description("Авторизация с верным паролем в другом регистре")
    @Test
    public void authorizationWithPassInDifferentRegister() {
        authorizationPage.visibilityAuth();
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.inputInFieldPassword("PASSWORD2");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();
    }

    @Description("Авторизация с невалидным логиом")
    @Test
    public void authorizationInvalidLogin() {
        authorizationPage.visibilityAuth();
        authorizationPage.inputInFieldLogin("qwerty");
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();

    }

    @Description("Авторизация с невалидным паролем")
    @Test
    public void authorizationInvalidPass() {
        authorizationPage.visibilityAuth();
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.inputInFieldPassword("qwerty");
        authorizationPage.pressButton();
        authorizationPage.visibilityAuth();
    }


}