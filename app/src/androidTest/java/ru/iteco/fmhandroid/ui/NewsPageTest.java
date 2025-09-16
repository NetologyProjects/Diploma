package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.ui.data.Utils;
import ru.iteco.fmhandroid.ui.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.ControlPanelNews;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;
import ru.iteco.fmhandroid.ui.pageObject.ThematicArticle;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NewsPageTest {


    AuthorizationPage authorizationPage = new AuthorizationPage();
    NewsPage newsPage = new NewsPage();
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    ControlPanelNews controlPanelNews = new ControlPanelNews();

    ThematicArticle thematicArticle = new ThematicArticle();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        Espresso.onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 10000));
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    @Description("Сортировка новостей")
    @Test
    public void sortingNews() {
        appBar.switchToNews();
        newsPage.buttonSortingNews();
    }

    @Description("Открытие формы 'Фильтровать новости'")
    @Test
    public void openFormFilterNews() {
        appBar.switchToNews();
        controlPanelNews.openFormFilterNews();
    }

    @Description("Открытие раздела 'Панель управления'")
    @Test
    public void openControlPanel() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
    }

    @Description("Переход на экран 'Главная', через навигационное меню")
    @Test
    public void openPageMain() {
        appBar.switchToNews();
        appBar.pageMain();
        mainPage.isDisplayedButtonProfile();
    }


    // тест падает, т.к. кнопка "О приложении" не активна
    @Description("Переход на экран 'О приложении' через навигационное меню")
    @Test
    public void openPageAboutApplication() {
        appBar.switchToNews();
        appBar.AboutApp();
    }

    @Description("Открытие экрана 'Главное - жить любя'")
    @Test
    public void shouldOpenPageWithThematicArticles() {
        appBar.pageOurMission();
        thematicArticle.textScreenCheckIsDisplayed();
    }

}
