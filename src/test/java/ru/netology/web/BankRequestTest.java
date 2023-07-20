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

    @Test
    void shouldCheckNameValidationFieldInvalid() throws InterruptedException {
        open("http://localhost:9999");
        $("[name=name]").setValue("Ggggggg");
        $("button").click();
        ElementsCollection form = $$(".input__sub");
        form.get(0).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        $(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
        Thread.sleep(5000);
    }

    @Test
    void shouldCheckNameValidationFieldEmpty() throws InterruptedException {
        open("http://localhost:9999");
        $("[name=phone]").setValue("+77777777777");
        $(".checkbox__box").click();
        $("button").click();
        ElementsCollection form = $$(".input__sub");
        form.get(0).shouldHave(text("Поле обязательно для заполнения"));
        $(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
        Thread.sleep(5000);
    }

    @Test
    void shouldCheckTelephoneValidationFieldInvalid() throws InterruptedException {
        open("http://localhost:9999");
        $("[name=name]").setValue("Аааааа");
        $("[name=phone]").setValue("+9999999999");
        $("button").click();
        ElementsCollection form = $$(".input__sub");
        form.get(1).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        $(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
        Thread.sleep(5000);
    }

    @Test
    void shouldCheckTelephoneValidationFieldEmpty() throws InterruptedException {
        open("http://localhost:9999");
        $("[name=name]").setValue("Аааааа");
        $(".checkbox__box").click();
        $("button").click();
        ElementsCollection form = $$(".input__sub");
        form.get(1).shouldHave(text("Поле обязательно для заполнения"));
        $(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
        Thread.sleep(5000);
    }

    @Test
    void shouldCheckCheckBoxValidationFieldEmpty() throws InterruptedException {
        open("http://localhost:9999");
        $("[name=name]").setValue("Аааааа");
        $("[name=phone]").setValue("+99999999999");
        $("button").click();
        $(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
        Thread.sleep(5000);
    }

    @Test
    void shouldNotSendRequestWhenAllFieldEmpty() throws InterruptedException {
        open("http://localhost:9999");
        $("button").click();
        ElementsCollection form = $$(".input__sub");
        form.get(0).shouldHave(text("Поле обязательно для заполнения"));
        $(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
        Thread.sleep(5000);
    }
}
