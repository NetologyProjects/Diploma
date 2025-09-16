package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.pageObject.Utils.waitDisplayed;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class ControlPanelNews {
    CreateNewsPage createNewsPage = new CreateNewsPage();
    EditNews editNews = new EditNews();
    FilterNews filterNews = new FilterNews();

    // Объявление идентификаторов кнопок
    private final int buttonAddNews = R.id.add_news_image_view;
    private final int buttonEditNews = R.id.edit_news_item_image_view;
    private final int buttonDeleteNews = R.id.delete_news_item_image_view;
    private final int buttonFilterNews = R.id.filter_news_material_button;
    private final ViewInteraction buttonOk = onView(withId(android.R.id.button1));
    public int getButtonAddNews() {
        return buttonAddNews;
    }

    @Step("Нажатие на кнопку 'Добавить новость'")
    public void addNews() {
        Allure.step("Нажатие на кнопку 'Добавить новость'");
        // Ождаем, что элемент виден и на него можно нажать
        onView(withId(buttonAddNews)).check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        onView(withId(buttonAddNews)).perform(click());
        // Ожидаем загрузку формы
        onView(isRoot()).perform(waitDisplayed(createNewsPage.getButtonSave(), 5000));
    }

    @Step("Нажатие на кнопку фильтрации новостей")
    public void openFormFilterNews() {
        Allure.step("Нажатие на кнопку фильтрации новостей");
        // Проверяем, что элемент видим и можно на него нажать
        onView(withId(buttonFilterNews)).check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        onView(withId(buttonFilterNews)).perform(ViewActions.click());
        // Ожидание загрузки формы
        onView(isRoot()).perform(Utils.waitDisplayed(filterNews.getFilter(), 5000));
    }

    @Step("Нажатие на кнопку 'Редактировать новость'")
    public void pressEditPanelNews(String title) {
        Allure.step("Нажатие на кнопку 'Редактировать новость'");
        onView(allOf(withId(buttonEditNews), hasSibling(withText(title)))).perform(click());
        // Ожидание загрузки формы
        onView(isRoot()).perform(Utils.waitDisplayed(editNews.getButtonSave(), 5000));
    }



    @Step("Нажатие на кнопку удаления новости")
    public void deleteNews(String title) {
        Allure.step("Нажатие на кнопку удаления новости");
        onView(allOf(withId(buttonDeleteNews), hasSibling(withText(title)))).perform(click());
        buttonOk.check(matches(isDisplayed()));
        buttonOk.perform(click());
    }

    @Step("Поиск новости с заголовком и проверка ее видимости")
    public void searchNewsAndCheckIsDisplayed(String text) {
        Allure.step("Поиск новости с заголовком и проверка ее видимости");//
        onView(withText(text)).check(matches(isDisplayed()));
    }

    @Step("Проверка отсутствия новости с заголовком")
    public void checkDoesNotExistNews(String text) {
        Allure.step("Проверка отсутствия новости с заголовком");
        onView(withText(text)).check(doesNotExist());
    }
}
