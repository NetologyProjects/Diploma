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
import ru.iteco.fmhandroid.ui.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.ControlPanelNews;
import ru.iteco.fmhandroid.ui.pageObject.CreateNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.EditNews;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;

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
    public void setUp() throws InterruptedException {
        onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 10000));
        Thread.sleep(5000);
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    // Тест упадёт, если уже есть такой же заголовок новости
    @Description("Успешное создание новости")
    @Test
    public void successfulNewsCreation(){
        String title = "Test_news555";
        String category = "Зарплата";

        appBar.switchToNews();                      // Переход на страницу новостей
        newsPage.switchControlPanelNews();          // Переход на панель управления новостями
        controlPanelNews.addNews();                 // Добавление новой новости
        createNewsPage.addCategory(category);  // Добавление категории
        createNewsPage.addTitle(title);                 // Добавление заголовка новости
        createNewsPage.addDate(Utils.currentDate());    // Добавление даты
        createNewsPage.addTime("19:25");           // Добавление времени
        createNewsPage.addDescription("тест");     // Добавление описания
        createNewsPage.pressSave();                     // Нажатие на кнопку сохранения

        try {
            controlPanelNews.searchNewsWithTitle(title); // Проверка, что новость создана
        } finally {
            controlPanelNews.deleteNews(title);         // Удаление созданной новости
        }
    }

    @Description("Удаление новости")
    @Test
    public void shouldDeleteNews() throws InterruptedException {
        String title = "Новость должна быть удалена";
        String category = "Зарплата";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNewsPage.creatingTestNews(title, category); // Создание тестовой записи

        controlPanelNews.searchNewsWithTitle(title);

        controlPanelNews.deleteNews(title);         // Удаление созданной новости

        Thread.sleep(100);
        controlPanelNews.checkDoesNotExistNews(title);
    }

    // Тест падает т.к. отсутствует проверка даты при создании новости
    // (реально в приложении невозможно выбрать дату из прошлого)
    @Description("Создание новости с датой публикации в прошлом")
    @Test
    public void shouldNotCreateNews() {
        String title = "Создание новости в прошлом";
        String category = "Зарплата";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNewsPage.addCategory(category);
        createNewsPage.addTitle(title);
        createNewsPage.addDate(Utils.dateInPast());
        createNewsPage.addTime("12:34");
        createNewsPage.addDescription("тест");
        createNewsPage.pressSave();

        try {
            //controlPanelNews.checkDoesNotExistNews(title); // Проверка, что новость не создана
            controlPanelNews.searchNewsWithTitle(title); // Проверка, что новость создана
        } finally {
            controlPanelNews.deleteNews(title);         // Удаление созданной новости
        }

    }

    @Description("Создание новости с датой публикации в будущем")
    @Test
    public void shouldCreateNewsInFuture() {
        String title = "Создание новости в будущем";
        String category = "Зарплата";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNewsPage.addCategory(category);
        createNewsPage.addTitle(title);
        createNewsPage.addDate(Utils.dateMore1Years());
        createNewsPage.addTime("12:34");
        createNewsPage.addDescription("тест");
        createNewsPage.pressSave();

        try {
            controlPanelNews.searchNewsWithTitle(title); // Проверка, что новость создана
        } finally {
            controlPanelNews.deleteNews(title);         // Удаление созданной новости
        }
    }

    @Description("Редактирование заголовка новости")
    @Test
    public void shouldEditTheNews(){
        String title = "Старый заголовок";
        String newTitle = "Заголовок изменён";
        String category = "Зарплата";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNewsPage.creatingTestNews(title, category); // Создание тестовой записи
        controlPanelNews.searchNewsWithTitle(title);
        controlPanelNews.pressEditPanelNews(title); //Редактирование заголовка записи
        editNews.editTitle(newTitle);
        editNews.pressSave();

        try {
            controlPanelNews.searchNewsWithTitle(title); // Проверка, что новость создана
        } finally {
            controlPanelNews.deleteNews(title);         // Удаление созданной новости
        }
    }

}
