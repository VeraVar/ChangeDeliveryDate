package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ChangeDeliveryDateTest {

    final DataGenerator generator = new DataGenerator();

    @Test
    void shouldChangeDeliveryDate(){
        open("http://localhost:9999/");
        String date = generator.generateDate();
        $("[data-test-id='city'] input").setValue(generator.generateCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generator.generateDate());
        $("[data-test-id='name'] input").setValue(generator.generateName());
        $("[data-test-id='phone'] input").setValue(generator.generatePhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $("[data-test-id='success-notification']").waitUntil(visible, 15000).shouldHave(text("Успешно! " +
                "Встреча успешно забронирована на " + date));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(date);
        $(".button").click();
        $("[data-test-id='replan-notification']").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(".notification_visible .button").click();
        $("[data-test-id='success-notification']").waitUntil(visible, 15000).shouldHave(text("Успешно! " +
                "Встреча успешно забронирована на " + date));
    }
}
