package ru.netology.web.test;


import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.MoneyTransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;


public class MoneyTransferTest {

    @Test
    void shouldValidTransfer() {
        open("http://localhost:9999", LoginPage.class);
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardData = DataHelper.getFirstCard();
        var secondCardData = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardData);
        var amount = DataHelper.validSum(firstCardBalance);
        var expectedBalanceCardOne = firstCardBalance - amount;
        var expectedBalanceCardTwo = secondCardBalance + amount;
        var moneyTransferPage = dashboardPage.chooseTransferCard(secondCardData);
        dashboardPage = moneyTransferPage.validTransfer(String.valueOf(amount), firstCardData);
        var actualBalanceCardOne = dashboardPage.getCardBalance(firstCardData);
        var actualBalanceCardTwo = dashboardPage.getCardBalance(secondCardData);
        assertEquals(expectedBalanceCardOne, actualBalanceCardOne);
        assertEquals(expectedBalanceCardTwo, actualBalanceCardTwo);
    }

    @Test
    void shouldNotTransfer() {
        open("http://localhost:9999", LoginPage.class);
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardData = DataHelper.getFirstCard();
        var secondCardData = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardData);
        var amount = DataHelper.inValidSum(secondCardBalance);
        var moneyTransferPage = dashboardPage.chooseTransferCard(firstCardData);
        moneyTransferPage.notValidTransfer(String.valueOf(amount), secondCardData);
        moneyTransferPage.getErrorAlarm("Выполнена попытка перевода суммы, превышающей остаток на карте списания");
        var actualBalanceCardOne = dashboardPage.getCardBalance(firstCardData);
        var actualBalanceCardTwo = dashboardPage.getCardBalance(secondCardData);
        assertEquals(firstCardBalance, actualBalanceCardOne);
        assertEquals(secondCardBalance, actualBalanceCardTwo);
    }
}

