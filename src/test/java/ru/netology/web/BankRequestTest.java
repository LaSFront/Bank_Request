package ru.netology.web;

import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class BankRequestTest {

    @Test
    void shouldSendRequest() throws InterruptedException {
        open("http://localhost:9999");

        $("h2").shouldHave(text("Заявка на дебетовую карту"));
        $("h3").shouldHave(text("Альфа-Карта"));

        ElementsCollection info = $$(".list__item");
        info.get(0).shouldHave(text("До 2% на все покупки"));
        info.get(1).shouldHave(text("До 6% годовых на остаток"));

        $(".Order_cardImage__Q69ii").shouldBe(visible);

        ElementsCollection form = $$(".input__control");
        form.get(0).setValue("Иванова Анна");
        form.get(1).setValue("+79999999999");

        $(".checkbox__box").click();
        $("button").click();

        $(".paragraph").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

        Thread.sleep(5000);
    }
}
