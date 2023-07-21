package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class BankRequestTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendRequest() {
        $$(".heading").find(exactText("Заявка на дебетовую карту")).shouldBe(visible);
        $$(".heading").find(exactText("Альфа-Карта")).shouldBe(visible);

        $$(".list__item").find(exactText("До 2% на все покупки")).shouldBe(visible);
        $$(".list__item").find(exactText("До 6% годовых на остаток")).shouldBe(visible);

        $(".Order_cardImage__Q69ii").shouldBe(visible);

        $("[data-test-id='name'] input").sendKeys("Иван Петров-Сидоров");
        $("[data-test-id='phone'] input").sendKeys("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button").shouldHave(exactText("Продолжить")).click();

        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldCheckNameValidationFieldInvalid() {
        $("[data-test-id='name'] input").sendKeys("Green7");
        $("[data-test-id='phone'] input").sendKeys("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button").shouldHave(exactText("Продолжить")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldCheckNameValidationFieldEmpty() {
        $("[data-test-id='name'] input").shouldBe(empty);
        $("[data-test-id='phone'] input").sendKeys("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button").shouldHave(exactText("Продолжить")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldCheckPhoneValidationFieldInvalid() {
        $("[data-test-id='name'] input").sendKeys("Петр Иванов");
        $("[data-test-id='phone'] input").sendKeys("7999999999");
        $("[data-test-id='agreement']").click();
        $("button").shouldHave(exactText("Продолжить")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldCheckPhoneValidationFieldEmpty() {
        $("[data-test-id='name'] input").sendKeys("Петр Иванов");
        $("[data-test-id='phone'] input").shouldBe(empty);
        $("[data-test-id='agreement']").click();
        $("button").shouldHave(exactText("Продолжить")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldCheckCheckBoxValidationFieldEmpty() {
        $("[data-test-id='name'] input").sendKeys("Петр Иванов");
        $("[data-test-id='phone'] input").sendKeys("+79999999999");
        $("[data-test-id='agreement']").shouldNotBe(checked);
        $("button").shouldHave(exactText("Продолжить")).click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(visible);
    }

    @Test
    void shouldNotSendRequestWhenAllFieldEmpty() {
        $("[data-test-id='name'] input").shouldBe(empty);
        $("[data-test-id='phone'] input").shouldBe(empty);
        $("[data-test-id='agreement']").shouldNotBe(checked);
        $("button").shouldHave(exactText("Продолжить")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
