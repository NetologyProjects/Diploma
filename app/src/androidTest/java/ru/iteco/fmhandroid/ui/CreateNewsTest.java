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
import ru.iteco.fmhandroid.ui.pageObject.CreateNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.EditNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class CreateNewsTest {

    AppBarPage appBarPage = new AppBarPage();
    MainPage mainPage = new MainPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();
    CreateNewsPage createNewsPage = new CreateNewsPage();
    EditNewsPage editNewsPage = new EditNewsPage();
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

    // Тест упадёт, если уже есть такой же заголовок новости
    @Description("Успешное создание новости")
    @Test
    public void successfulNewsCreation() {
        String title = "Test_news555";
        String category = "Зарплата";

        appBarPage.switchToNews();                      // Переход на страницу новостей
        newsPage.switchControlPanelNews();          // Переход на панель управления новостями
        controlPanelPage.addNews();                 // Добавление новой новости
        createNewsPage.addCategory(category);  // Добавление категории
        createNewsPage.addTitle(title);                 // Добавление заголовка новости
        createNewsPage.addDate(Utils.currentDate());    // Добавление даты
        createNewsPage.addTime("19:25");           // Добавление времени
        createNewsPage.addDescription("тест");     // Добавление описания
        createNewsPage.pressSave();                     // Нажатие на кнопку сохранения

        try {
            controlPanelPage.searchNewsWithTitle(title); // Проверка, что новость создана
        } finally {
            controlPanelPage.deleteNews(title);         // Удаление созданной новости
        }
    }

    @Description("Удаление новости")
    @Test
    public void shouldDeleteNews() throws InterruptedException {
        String title = "Новость должна быть удалена";
        String category = "Зарплата";

        appBarPage.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelPage.addNews();
        createNewsPage.creatingTestNews(title, category); // Создание тестовой записи

        controlPanelPage.searchNewsWithTitle(title);

        controlPanelPage.deleteNews(title);         // Удаление созданной новости

        Thread.sleep(100);
        controlPanelPage.checkDoesNotExistNews(title);
    }

    // Тест падает т.к. отсутствует проверка даты при создании новости
    // (реально в приложении невозможно выбрать дату из прошлого)
    @Description("Создание новости с датой публикации в прошлом")
    @Test
    public void shouldNotCreateNews() {
        String title = "Создание новости в прошлом";
        String category = "Зарплата";

        appBarPage.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelPage.addNews();
        createNewsPage.addCategory(category);
        createNewsPage.addTitle(title);
        createNewsPage.addDate(Utils.dateInPast());
        createNewsPage.addTime("12:34");
        createNewsPage.addDescription("тест");
        createNewsPage.pressSave();

        try {
            controlPanelPage.checkDoesNotExistNews(title); // Проверка, что новость не создана
            //controlPanelPage.searchNewsWithTitle(title); // Проверка, что новость создана
        } finally {
            controlPanelPage.deleteNews(title);         // Удаление созданной новости
        }

    }

    @Description("Создание новости с датой публикации в будущем")
    @Test
    public void shouldCreateNewsInFuture() {
        String title = "Создание новости в будущем";
        String category = "Зарплата";

        appBarPage.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelPage.addNews();
        createNewsPage.addCategory(category);
        createNewsPage.addTitle(title);
        createNewsPage.addDate(Utils.dateMore1Years());
        createNewsPage.addTime("12:34");
        createNewsPage.addDescription("тест");
        createNewsPage.pressSave();

        try {
            controlPanelPage.searchNewsWithTitle(title); // Проверка, что новость создана
        } finally {
            controlPanelPage.deleteNews(title);         // Удаление созданной новости
        }
    }

    @Description("Редактирование заголовка новости")
    @Test
    public void shouldEditTheNews() {
        String title = "Старый заголовок";
        String newTitle = "Заголовок изменён";
        String category = "Зарплата";

        appBarPage.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelPage.addNews();
        createNewsPage.creatingTestNews(title, category); // Создание тестовой записи
        controlPanelPage.searchNewsWithTitle(title);
        controlPanelPage.pressEditPanelNews(title); //Редактирование заголовка записи
        editNewsPage.editTitle(newTitle);
        editNewsPage.pressSave();

        try {
            controlPanelPage.searchNewsWithTitle(newTitle); // Проверка, что новость изменена
        } finally {
            controlPanelPage.deleteNews(newTitle);         // Удаление созданной новости
        }
    }

}
