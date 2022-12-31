package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.regex.Pattern;


public class DashboardPage {


    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " p.";
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardData cardData) {
        var text = cards.findBy(Condition.text(cardData.getCardNum().substring(15))).getText();
        return extractBalance(text);
    }

    public MoneyTransferPage chooseTransferCard(DataHelper.CardData cardData) {
        cards.findBy(attribute("data-test-id", cardData.getId())).$("button").click();
        return new MoneyTransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var sum = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(sum);

    }
}

