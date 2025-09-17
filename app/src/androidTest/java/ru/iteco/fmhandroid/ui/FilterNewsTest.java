package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

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
import ru.iteco.fmhandroid.ui.pageObject.FilterNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class FilterNewsTest {

    AppBarPage appBarPage = new AppBarPage();
    FilterNewsPage filterNewsPage = new FilterNewsPage();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();


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

    @Description("Фильтрация новостей с пустой формой")
    @Test
    public void filterNewsEmptyForm() {
        appBarPage.switchToNews();
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter("");
        filterNewsPage.setDateFromFilter("");
        filterNewsPage.setDateToFilter("");
        filterNewsPage.confirmFilter();
        newsPage.checkNews();
    }

    @Description("Фильтрация новостей с валидным периодом дат")
    @Test
    public void filterNewsValidDate() {
        appBarPage.switchToNews();
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter("День рождения");
        filterNewsPage.setDateFromFilter(Utils.currentDate());
        filterNewsPage.setDateToFilter(Utils.currentDate());
        filterNewsPage.confirmFilter();
        newsPage.checkNews();
    }

    @Description("Фильтрация новостей по категории")
    @Test
    public void filterNewsByCategory() {
        appBarPage.switchToNews();
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter("Зарплата");
        filterNewsPage.confirmFilter();
        newsPage.checkNews();
    }

    @Description("Фильтрация новостей по дате ОТ")
    @Test
    public void filterNewsByDateFrom() {
        appBarPage.switchToNews();
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter("День рождения");
        filterNewsPage.setDateFromFilter(Utils.currentDate());
        filterNewsPage.confirmFilter();
        filterNewsPage.checkErrorFilterNews("Неверно указан период");
    }

    @Description("Фильтрация новостей по дате ДО")
    @Test
    public void filterNewsByDateTo() {
        appBarPage.switchToNews();
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter("День рождения");
        filterNewsPage.setDateToFilter(Utils.currentDate());
        filterNewsPage.confirmFilter();
        filterNewsPage.checkErrorFilterNews("Неверно указан период");
    }

    @Description("Фильтрация новостей по указанному периоду (текущая дата)")
    @Test
    public void filterNewsByDatePeriod() {
        appBarPage.switchToNews();
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setDateFromFilter(Utils.currentDate());
        filterNewsPage.setDateToFilter(Utils.currentDate());
        filterNewsPage.confirmFilter();
        newsPage.checkNews();
    }

    @Description("Фильтрация новостей с валидно указанным периодом (прошлое-будущее)")
    @Test
    public void filterNewsValidPeriod() {
        appBarPage.switchToNews();
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter("День рождения");
        filterNewsPage.setDateFromFilter(Utils.dateMore1Years());
        filterNewsPage.setDateToFilter(Utils.currentDate());
        filterNewsPage.confirmFilter();
        newsPage.checkNews();
    }

    @Description("Отображение сообщения 'Здесь пока ничего нет' (период в будущем)")
    @Test
    public void filterNewsDateInPast() {
        appBarPage.switchToNews();
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter("День рождения");
        filterNewsPage.setDateFromFilter(Utils.dateMore1Month());
        filterNewsPage.setDateToFilter(Utils.dateMore1Month());
        filterNewsPage.confirmFilter();
        filterNewsPage.elementThereNothingHereYet();
    }

    // тест падает - баг (Сообщение об ошибке не отображается)
    @Description("Фильтрация новостей, где первая дата в будущем, вторая в прошлом")
    @Test
    public void shouldCheckErrorWithInvalidDates() {
        appBarPage.switchToNews();
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter("День рождения");
        filterNewsPage.setDateFromFilter(Utils.dateMore1Month());
        filterNewsPage.setDateToFilter(Utils.dateInPast());
        filterNewsPage.confirmFilter();
        filterNewsPage.checkErrorFilterNews("Неверно указан период");
    }

    @Description("Отмена фильтрации кнопкой 'Отмена'")
    @Test
    public void filterNewsCancel() {
        appBarPage.switchToNews();
        controlPanelPage.openFormFilterNews();
        filterNewsPage.setCategoryFilter("День рождения");
        filterNewsPage.setDateFromFilter(Utils.currentDate());
        filterNewsPage.setDateToFilter(Utils.currentDate());
        filterNewsPage.cancelFilter();
        newsPage.checkNews();
    }
}
