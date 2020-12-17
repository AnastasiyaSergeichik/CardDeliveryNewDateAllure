package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

import static org.openqa.selenium.By.cssSelector;
import static ru.netology.DataGenerator.Registration.*;

public class CardDeliveryNewDateTest {
    private SelenideElement form;
    private final String invalidCity = generateInvalidCity();
    private final String firstDate = generateDate(3);
    private final String secondDate = generateDate(7);
    private final String invalidPhone = generateInvalidPhone();

    @BeforeEach
    void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("http://localhost:9999");
        form = $("[action]");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    void shouldSubmitRequest() {
        form.$("[data-test-id=city] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(firstDate);
        form.$("[data-test-id=name] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getName());
        form.$("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(withText("Успешно!")).shouldBe(visible);
        $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(firstDate));
    }

    @Test
    void shouldSubmitRequestNewDate() {
        form.$("[data-test-id=city] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(firstDate);
        form.$("[data-test-id=name] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getName());
        form.$("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getPhone());
        form.$("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Успешно")).waitUntil(visible, 15000);
        $(".button__text").click();
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).waitUntil(visible, 15000);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(secondDate);
        $(cssSelector(".notification_status_error .button")).click();
        $(withText("Успешно!")).shouldBe(visible);
        $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(secondDate));
    }

    @Test
    void shouldSubmitRequestWithoutCity() {
        form.$("[data-test-id=date] input").setValue(firstDate);
        form.$("[data-test-id=name] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getName());
        form.$("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSubmitRequestInvalidCity() {
        form.$("[data-test-id=city] input").setValue(invalidCity);
        form.$("[data-test-id=date] input").setValue(firstDate);
        form.$("[data-test-id=name] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getName());
        form.$("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldSubmitRequestWithoutName() {
        form.$("[data-test-id=city] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getCity());
        form.$("[data-test-id=date] input").setValue(firstDate);
        form.$("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSubmitInvalidName() {
        form.$("[data-test-id=city] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getCity());
        form.$("[data-test-id=date] input").setValue(firstDate);
        $("[name=name]").setValue(DataGenerator.Registration.generateMeetingInfo("en").getName());
        form.$("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSubmitRequestWithoutPhone() {
        form.$("[data-test-id=city] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getCity());
        form.$("[data-test-id=date] input").setValue(firstDate);
        form.$("[data-test-id=name] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getName());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSubmitInvalidPhone() {
        form.$("[data-test-id=city] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getCity());
        form.$("[data-test-id=date] input").setValue(firstDate);
        form.$("[data-test-id=name] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getName());
        form.$("[data-test-id=phone] input").setValue(invalidPhone);
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSubmitRequestWithoutCheckbox() {
        form.$("[data-test-id=city] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getCity());
        form.$("[data-test-id=date] input").setValue(firstDate);
        form.$("[data-test-id=name] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getName());
        form.$("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateMeetingInfo("ru").getPhone());
        form.$(".button").click();
        $(".input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}

