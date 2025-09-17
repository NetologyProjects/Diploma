package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.ui.data.Utils;
import ru.iteco.fmhandroid.ui.pageObject.AppBarPage;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class MainPageTest {

    AuthorizationPage authorizationPage = new AuthorizationPage();
    AppBarPage appBarPage = new AppBarPage();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        onView(isRoot()).perform(Utils.waitDisplayed(appBarPage.getAppBarFragmentMain(), 15000));
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    @Description("Открытие навигационного меню")
    @Test
    public void openNavigationMenu() {
        appBarPage.buttonMainMenu.perform(click());
        onView(withText("Новости")).check(matches(isDisplayed()));
        onView(withText("О приложении")).check(matches(isDisplayed()));
        onView(withText("Главная")).check(matches(isDisplayed()));
    }

    @Description("Переход на экран 'Новости'")
    @Test
    public void openPageNews() {
        appBarPage.switchToNews();
        newsPage.checkNews();
    }

    @Description("Переход на экран 'О приложении'")
    @Test
    public void openPageAboutApplication() {
        mainPage.isDisplayedButtonProfile();
        appBarPage.AboutApp();
    }

    @Description("Переход на экран 'Новости' через вкладку 'Все новости'")
    @Test
    public void openPageNewsThroughAllNewsTab() {
        mainPage.setButtonAllNews();
        newsPage.checkNews();
    }

    @Description("Выход из ЛК пользователя")
    @Test
    public void logOut() {
        appBarPage.logOut();
    }


}
