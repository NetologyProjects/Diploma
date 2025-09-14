package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.ControlPanelNews;
import ru.iteco.fmhandroid.ui.pageObject.CreateNews;
import ru.iteco.fmhandroid.ui.pageObject.EditNews;
import ru.iteco.fmhandroid.ui.pageObject.FilterNews;
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
    CreateNews createNews = new CreateNews();
    EditNews editNews = new EditNews();
    NewsPage newsPage = new NewsPage();
    FilterNews filterNews = new FilterNews();

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

    // Тест упадёт, если есть такой же заголовок новости
    @Description("Успешное создание новости")
    @Test
    public void successfulNewsCreation() {
        String title = "Test_news444";

        // Переход на страницу новостей
        appBar.switchToNews();
        // Переход на панель управления новостями
        newsPage.switchControlPanelNews();
        // Добавление новой новости
        controlPanelNews.addNews();
        // Добавление категории
        createNews.addCategory("Объявление");
        // Добавление заголовка новости
        createNews.addTitle(title);
        // Добавление даты
        createNews.addDate(Utils.currentDate());
        // Добавление времени
        createNews.addTime("19:25");
        // Добавление описания
        createNews.addDescription("тест");
        // Нажатие на кнопку сохранения
        createNews.pressSave();
        // Проверяем, что новость создана
        controlPanelNews.searchNewsAndCheckIsDisplayed(title);
        // Удаляем созданную новость
        controlPanelNews.deleteNews(title);
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
        createNews.addCategory("Объявление");
        createNews.addTitle(title);
        String pastDate = Utils.dateInPast();
        createNews.addDate(pastDate);
        createNews.addTime("12:34");
        createNews.addDescription("тест");
        createNews.pressSave();
        controlPanelNews.checkDoesNotExistNews(title);
        // Удаляем созданную новость
        controlPanelNews.deleteNews(title);
    }

    @Description("Создание новости с датой публикации в будущем")
    @Test
    public void shouldCreateTneNewsInFuture() {
        String title = "Создание новости в будущем";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.addCategory("Объявление");
        createNews.addTitle(title);
        String pastDate = Utils.dateMore1Years();
        createNews.addDate(pastDate);
        createNews.addTime("12:34");
        createNews.addDescription("тест");
        createNews.pressSave();
        controlPanelNews.searchNewsAndCheckIsDisplayed(title);
        // Удаляем созданную новость
        controlPanelNews.deleteNews(title);
    }


    @Description("Удаление новости")
    @Test
    public void shouldDeleteNews() throws InterruptedException {
        String title = "Новость должна быть удалена";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        // Создание тестовой записи
        createNews.creatingTestNews(title);
        controlPanelNews.searchNewsAndCheckIsDisplayed(title);
        // Удаляем созданную новость
        controlPanelNews.deleteNews(title);
        Thread.sleep(1000);
        controlPanelNews.checkDoesNotExistNews(title);
    }

    // тест аналогично падает с ошибкой AmbiguousViewMatcherException
    // тест пытается взаимодействовать с элементом интерфейса (значок редактирования),
    // который имеет несколько экземпляров в иерархии представлений
    // тест не знает какой именно элемент использовать, так как у них одинаковые идентификаторы
    @Description("Редактирование категории новости")
    @Test
    public void shouldEditCategoryOfNews() throws InterruptedException {
        String title = "Тест655";
        String category = "Зарплата";

        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        // Создание тестовой записи
        createNews.creatingTestNews(title);
        controlPanelNews.searchNewsAndCheckIsDisplayed(title);
        //Редактирование категории записи
        controlPanelNews.pressEditPanelNews(title);
        editNews.editCategory(category);
        editNews.pressSave();
        Thread.sleep(2000);
//        //Фильтрация записей по категории
//        filterNews.addCategoryFilter(category);
//        filterNews.confirmFilter();
        //Проверка наличия тестовой записи в выбранной категории
        Thread.sleep(2000);
//        controlPanelNews.searchNewsAndCheckIsDisplayed(title);
        onView(allOf(withId(R.id.news_item_title_text_view), withText("Тест669"),
                        withParent(withParent(withId(R.id.news_item_material_card_view))),
                        isDisplayed()))
        .check(matches(withText("Тест669")));
    }

    // все ПОСЛЕДУЮЩИЕ тесты, связанные с редактированием, будут также падать
    // причина - та же. На примере с редактированием заголовка:
    @Description("Редактирование заголовка новости")
    @Test
    public void shouldEditTheNews() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.searchNewsAndCheckIsDisplayed("Создание новости в будущем");
        controlPanelNews.pressEditPanelNews("ttt");
        editNews.editTitle("Crocodile");
        editNews.pressSave();
    }

}
