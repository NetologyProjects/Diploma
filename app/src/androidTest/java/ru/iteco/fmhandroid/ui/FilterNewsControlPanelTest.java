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
import ru.iteco.fmhandroid.ui.pageObject.AppBarPage;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.ControlPanelPage;
import ru.iteco.fmhandroid.ui.pageObject.CreateNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.FilterNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class FilterNewsControlPanelTest {

    AppBarPage appBarPage = new AppBarPage();
    FilterNewsPage filterNewsPage = new FilterNewsPage();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();
    CreateNewsPage createNewsPage = new CreateNewsPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        Espresso.onView(isRoot()).perform(Utils.waitDisplayed(appBarPage.getAppBarFragmentMain(), 15000));
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    @Description("Фильтрация новостей с активными чек-боксами")
    @Test
    public void filterNewsWithCheckBoxes() throws InterruptedException {
        String title = "Test222";
        String category = "Массаж";

        appBarPage.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelPage.addNews();
        createNewsPage.creatingTestNews(title, category); // Создание тестовой записи
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter(category);
        filterNewsPage.setDateFromFilter(Utils.currentDate());
        filterNewsPage.setDateToFilter(Utils.currentDate());
        filterNewsPage.confirmFilter();

        Thread.sleep(100);
        controlPanelPage.searchNewsWithTitle(title);

        controlPanelPage.deleteNews(title);         // Удаление созданной новости
    }

    @Description("Фильтрация новостей c неактивным чек-боксом")
    @Test
    public void filterNewsWithoutCheckBoxes() throws InterruptedException {
        String title = "Test333";
        String category = "Массаж";

        appBarPage.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelPage.addNews();
        createNewsPage.creatingTestNews(title, category); // Создание тестовой записи
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter(category);
        filterNewsPage.setDateFromFilter(Utils.currentDate());
        filterNewsPage.setDateToFilter(Utils.currentDate());
        filterNewsPage.setCheckBoxActive(); // Изменить чекбокс (отключить активные)
        filterNewsPage.confirmFilter();

        // Поиск новости в неактивных (должно быть ненайдено)
        Thread.sleep(100);
        controlPanelPage.checkDoesNotExistNews(title);

        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter(category);
        filterNewsPage.setDateFromFilter(Utils.currentDate());
        filterNewsPage.setDateToFilter(Utils.currentDate());
        filterNewsPage.setCheckBoxInActive(); // Изменить чекбокс (отключить неактивные)
        filterNewsPage.confirmFilter();

        // Поиск новости в активных (должно быть найдено)
        Thread.sleep(100);
        controlPanelPage.searchNewsWithTitle(title);

        controlPanelPage.deleteNews(title);         // Удаление созданной новости
    }

}
