package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.byText;

public class MoneyTransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromCardField = $("[data-test-id=from] input");

    private SelenideElement addButton = $("[data-test-id='action-transfer']");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private SelenideElement errorAlarm = $("[data-test-id='error-message']");
    private SelenideElement transferHead = $(byText("Пополнение карты"));


    public MoneyTransferPage() {
        transferHead.shouldBe(visible);
    }

    public DashboardPage validTransfer(String transferSum, DataHelper.CardData cardData) {
        notValidTransfer(transferSum, cardData);
        return new DashboardPage();
    }

    public void notValidTransfer(String transferSum, DataHelper.CardData cardData) {
        amountField.setValue(transferSum);
        fromCardField.setValue(cardData.getCardNum());
        addButton.click();
    }

    public void getErrorAlarm(String expectedText) {
        errorAlarm.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

}
