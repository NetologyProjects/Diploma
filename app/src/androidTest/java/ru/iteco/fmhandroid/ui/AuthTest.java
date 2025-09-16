package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static ru.iteco.fmhandroid.ui.pageObject.Utils.waitDisplayed;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.ui.elements.Toast;
import ru.iteco.fmhandroid.ui.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AuthTest {
    AuthorizationPage authorizationPage = new AuthorizationPage();
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    Toast toast = new Toast();
    String validLogin = "login2";
    String validPassword = "password2";
    private View decorView;


    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        onView(isRoot()).perform(waitDisplayed(appBar.getAppBarFragmentMain(), 15000));
        if (mainPage.isDisplayedButtonProfile()) {
            appBar.logOut(); //Выход из аккаунта
        }
        authorizationPage.visibilityAuth(); // Проверка видимости экрана 'Авторизация'


        mActivityScenarioRule.getScenario().onActivity(new ActivityScenario.ActivityAction<AppActivity>() {
            @Override
            public void perform(AppActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });
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
        toast.emptyLoginOrPassword(decorView); //Проверка тост-сообщения
    }

    @Description("Авторизация с пустым паролем")
    @Test
    public void authorizationWithEmptyPass() throws InterruptedException {
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.pressButton();

        Thread.sleep(100);
        toast.emptyLoginOrPassword(decorView); //Проверка тост-сообщения
    }

    @Description("Авторизация с пробелом вместо логина")
    @Test
    public void authorizationSpacesInLogin() throws InterruptedException {
        authorizationPage.inputInFieldLogin(" ");
        authorizationPage.inputInFieldPassword(validPassword);
        authorizationPage.pressButton();

        Thread.sleep(100);
        toast.emptyLoginOrPassword(decorView); //Проверка тост-сообщения
    }

    @Description("Авторизация с пробелом вместо пароля")
    @Test
    public void authorizationSpacesInPass() throws InterruptedException {
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.inputInFieldPassword(" ");
        authorizationPage.pressButton();

        Thread.sleep(100);
        toast.emptyLoginOrPassword(decorView); //Проверка тост-сообщения
    }

    @Description("Авторизация с верным логином в другом регистре")
    @Test
    public void authorizationWithLoginInDifferentRegister() throws InterruptedException {
        authorizationPage.inputInFieldLogin("LOGIN2");
        authorizationPage.inputInFieldPassword(validPassword);
        authorizationPage.pressButton();

        Thread.sleep(100);
        toast.error(decorView); //Проверка тост-сообщения
    }

    @Description("Авторизация с верным паролем в другом регистре")
    @Test
    public void authorizationWithPassInDifferentRegister() throws InterruptedException {
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.inputInFieldPassword("PASSWORD2");
        authorizationPage.pressButton();

        Thread.sleep(100);
        toast.error(decorView); //Проверка тост-сообщения
    }

    @Description("Авторизация с невалидным логиом")
    @Test
    public void authorizationInvalidLogin() throws InterruptedException {
        authorizationPage.inputInFieldLogin("qwerty");
        authorizationPage.inputInFieldPassword(validPassword);
        authorizationPage.pressButton();

        Thread.sleep(100);
        toast.error(decorView); //Проверка тост-сообщения
    }

    @Description("Авторизация с невалидным паролем")
    @Test
    public void authorizationInvalidPass() throws InterruptedException {
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.inputInFieldPassword("qwerty");
        authorizationPage.pressButton();

        Thread.sleep(100);
        toast.error(decorView); //Проверка тост-сообщения
    }


}