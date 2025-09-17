package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Utils;

public class AppBarPage {
    MainPage mainPage = new MainPage();
    ThematicArticlePage thematicArticlePage = new ThematicArticlePage();
    NewsPage newsPage = new NewsPage();
    int appBarFragmentMain = R.id.container_custom_app_bar_include_on_fragment_main;

    public int getAppBarFragmentMain() {
        return appBarFragmentMain;
    }

    int pressProfile = R.id.authorization_image_button;

    public int getPressProfile() {
        return pressProfile;
    }

    int buttonBack = R.id.about_back_image_button;


    public ViewInteraction mainMenuNews = onView(
            allOf(withId(android.R.id.title), withText("Новости")));

    public ViewInteraction mainMenuAboutApp = onView(
            allOf(withId(android.R.id.title), withText("О приложении")));

    public ViewInteraction mainMenuMain = onView(
            allOf(withId(android.R.id.title), withText("Главная")));
    public ViewInteraction buttonMainMenu = onView(withId(R.id.main_menu_image_button));

    public ViewInteraction buttonOurMission = onView(withId(R.id.our_mission_image_button));

    @Step("Выход из аккаунта")
    public void logOut() {
        Allure.step("Выход из аккаунта");
        onView(withId(R.id.authorization_image_button)).perform(click());
        onView(withText("Выйти")).check(matches(isDisplayed())).perform(click());
    }

    @Step("Переход на экран 'Новости'")
    public void switchToNews() {
        Allure.step("Переход на экран 'Новости'");
        buttonMainMenu.check(matches(isDisplayed()));
        buttonMainMenu.perform(click());
        mainMenuNews.check(matches(isDisplayed()));
        mainMenuNews.perform(click());
        onView(isRoot()).perform(Utils.waitDisplayed(newsPage.getContainerNews(), 5000));
    }

    @Step("Переход на экран 'О приложении'")
    public void AboutApp() {
        Allure.step("Переход на экран 'О приложении'");
        buttonMainMenu.check(matches(isDisplayed()));
        buttonMainMenu.perform(click());
        mainMenuAboutApp.check(matches(isDisplayed()));
        mainMenuAboutApp.perform(click());
        onView(isRoot()).perform(Utils.waitDisplayed(buttonBack, 5000));
    }

    @Step("Переход на экран 'Главная'")
    public void pageMain() {
        Allure.step("Переход на экран 'Главная'");
        buttonMainMenu.check(matches(isDisplayed()));
        buttonMainMenu.perform(click());
        mainMenuMain.check(matches(isDisplayed()));
        mainMenuMain.perform(click());
        onView(isRoot()).perform(Utils.waitDisplayed(mainPage.getContainerNews(), 5000));
    }

    @Step("Переход на экран 'Главное-жить любя'")
    public void pageOurMission() {
        Allure.step("Переход на экран цитат");
        buttonOurMission.check(matches(isDisplayed()));
        buttonOurMission.perform(click());
        onView(isRoot()).perform(Utils.waitDisplayed(thematicArticlePage.getTextScreen(), 5000));
    }
}
