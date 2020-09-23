package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import static ru.netology.DataGenerator.generateDate;

public class ChangeDeliveryDateTest {

    final DataGenerator generator = new DataGenerator();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    void shouldChangeDeliveryDate(){
        open("http://localhost:9999/");
        String dateFirstMeeting = generateDate(3);
        String dateSecondMeeting = generateDate(5);
        $("[data-test-id='city'] input").setValue(generator.generateCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateFirstMeeting);
        $("[data-test-id='name'] input").setValue(generator.generateName());
        $("[data-test-id='phone'] input").setValue(generator.generatePhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $("[data-test-id='success-notification']").waitUntil(visible, 15000).shouldHave(text("Успешно! " +
                "Встреча успешно запланирована на " + dateFirstMeeting));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(dateSecondMeeting);
        $(".button").click();
        $("[data-test-id='replan-notification']").shouldHave(text("Необходимо подтверждение " + "У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(".notification_visible .button").click();
        $("[data-test-id='success-notification']").waitUntil(visible, 15000).shouldHave(text("Успешно! " +
                "Встреча успешно запланирована на " + dateSecondMeeting));
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
}
