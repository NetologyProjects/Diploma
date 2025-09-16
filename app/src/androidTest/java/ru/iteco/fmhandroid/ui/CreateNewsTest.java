package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import android.util.Log;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.ui.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.ControlPanelNews;
import ru.iteco.fmhandroid.ui.pageObject.CreateNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.EditNews;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;
import ru.iteco.fmhandroid.ui.pageObject.Utils;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class CreateNewsTest {

    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();
    ControlPanelNews controlPanelNews = new ControlPanelNews();
    CreateNewsPage createNewsPage = new CreateNewsPage();
    EditNews editNews = new EditNews();
    NewsPage newsPage = new NewsPage();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 10000));
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    // Тест упадёт, если уже есть такой же заголовок новости
    @Description("Успешное создание новости")
    @Test
    public void successfulNewsCreation() throws InterruptedException {
        String title = "Test_news555";

        appBar.switchToNews();                      // Переход на страницу новостей
        newsPage.switchControlPanelNews();          // Переход на панель управления новостями
        controlPanelNews.addNews();                 // Добавление новой новости
        createNewsPage.addCategory("Объявление");  // Добавление категории
        createNewsPage.addTitle(title);                 // Добавление заголовка новости
        createNewsPage.addDate(Utils.currentDate());    // Добавление даты
        createNewsPage.addTime("19:25");           // Добавление времени
        createNewsPage.addDescription("тест");     // Добавление описания
        createNewsPage.pressSave();                     // Нажатие на кнопку сохранения

        Thread.sleep(100);
        controlPanelNews.searchNewsAndCheckIsDisplayed(title); // Проверка, что новость создана

        controlPanelNews.deleteNews(title);         // Удаление созданной новости
    }

    // Тест падает т.к. отсутствует проверка даты при создании новости
// (реально в приложении невозможно выбрать дату из прошдого)
    @Description("Создание новости с датой публикации в прошлом")
    @Test
    public void shouldNotCreateNews() {
        String title = "Создание новости в прошлом";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNewsPage.addCategory("Объявление");
        createNewsPage.addTitle(title);
        createNewsPage.addDate(Utils.dateInPast());
        createNewsPage.addTime("12:34");
        createNewsPage.addDescription("тест");
        createNewsPage.pressSave();

        try {
            controlPanelNews.checkDoesNotExistNews(title);
        } catch (Throwable e) {
            Log.i("ESPRESSO", "Создание новости с датой публикации в прошлом -> не пройден");
            throw e;
        } finally {
            controlPanelNews.deleteNews(title);         // Удаление созданной новости
        }

    }

    @Description("Создание новости с датой публикации в будущем")
    @Test
    public void shouldCreateNewsInFuture() {
        String title = "Создание новости в будущем";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNewsPage.addCategory("Объявление");
        createNewsPage.addTitle(title);
        createNewsPage.addDate(Utils.dateMore1Years());
        createNewsPage.addTime("12:34");
        createNewsPage.addDescription("тест");
        createNewsPage.pressSave();

        controlPanelNews.searchNewsAndCheckIsDisplayed(title);

        controlPanelNews.deleteNews(title);         // Удаление созданной новости
    }


    @Description("Удаление новости")
    @Test
    public void shouldDeleteNews() throws InterruptedException {
        String title = "Новость должна быть удалена";
        String category = "Зарплата";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        // Создание тестовой записи
        createNewsPage.creatingTestNews(title, category);

        controlPanelNews.searchNewsAndCheckIsDisplayed(title);

        controlPanelNews.deleteNews(title);         // Удаление созданной новости

        Thread.sleep(100);
        controlPanelNews.checkDoesNotExistNews(title);
    }

    @Description("Редактирование заголовка новости")
    @Test
    public void shouldEditTheNews() throws InterruptedException {
        String title = "Старый заголовок";
        String newTitle = "Заголовок изменён";
        String category = "Зарплата";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNewsPage.creatingTestNews(title, category); // Создание тестовой записи
        controlPanelNews.searchNewsAndCheckIsDisplayed(title);
        controlPanelNews.pressEditPanelNews(title); //Редактирование категории записи
        editNews.editTitle(newTitle);
        editNews.pressSave();

        Thread.sleep(100);
        controlPanelNews.searchNewsAndCheckIsDisplayed(newTitle);

        controlPanelNews.deleteNews(newTitle);         // Удаление созданной новости
    }

}
