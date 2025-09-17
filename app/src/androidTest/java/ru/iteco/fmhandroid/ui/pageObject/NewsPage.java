package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Utils;

public class NewsPage {
    ControlPanelPage controlPanelPage = new ControlPanelPage();
    private final int buttonSortingNews = R.id.sort_news_material_button;
    private final int buttonControlPanel = R.id.edit_news_material_button;
    private final int containerPageNews = R.id.container_list_news_include;

    public int getContainerNews() {
        return containerPageNews;
    }

    @Step("Проверка видимости 'Новости'")
    public void checkNews() {
        Allure.step("Проверка видимости элемента с текстом 'Новости'");
        onView(isRoot()).perform(Utils.waitDisplayed(containerPageNews, 5000));
        onView(withText("Новости")).check(matches(withText("Новости")));
    }

    @Step("Нажатие на кнопку 'Сортировать' новости")
    public void buttonSortingNews() {
        Allure.step("Нажатие на кнопку 'Сортировать' новости");
        // ожидаем, что элемент виден на экране, и на него можно нажать
        onView(withId(buttonSortingNews)).check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        onView(withId(buttonSortingNews)).perform(ViewActions.click());
    }


    @Step("Переход на 'Панель управления'")
    public void switchControlPanelNews() {
        Allure.step("Переход на 'Панель управления'");
        //  Переход на страницу с панелью управления
        onView(isRoot()).perform(Utils.waitDisplayed(buttonControlPanel, 5000));
        // Проверяем, что элемент видно и что можно нажать
        onView(withId(buttonControlPanel)).check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        onView(withId(buttonControlPanel)).perform(ViewActions.click());
        // Ожидаем загрузку панели управления
        onView(isRoot()).perform(Utils.waitDisplayed(controlPanelPage.getButtonAddNews(), 5000));
    }

}
