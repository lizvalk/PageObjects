package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;

public class TransferTest {
    DashboardPage dashboardPage;
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }
    @Test
    void shouldTransferFromFirstToSecondCard() {
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard  = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.selectSecondCardToTransfer();
        dashboardPage = transferPage.successfulMoneyTransfer(String.valueOf(amount), firstCard);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        Assertions.assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        Assertions.assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }
    @Test
    void shouldTransferFromSecondToFirstCard() {
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = generateValidAmount(secondCardBalance);
        var expectedBalanceFirstCard  = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var transferPage = dashboardPage.selectFirstCardToTransfer();
        dashboardPage = transferPage.successfulMoneyTransfer(String.valueOf(amount), secondCard);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        Assertions.assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        Assertions.assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldGetErrorMessageIfDoNotFillCardNumberField() {
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        var invalidCard = getInvalidCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = generateValidAmount(secondCardBalance);
        var expectedBalanceFirstCard  = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var transferPage = dashboardPage.selectFirstCardToTransfer();
        dashboardPage = transferPage.successfulMoneyTransfer(String.valueOf(amount), invalidCard);
        transferPage.ErrorMessage("Ошибка! Произошла ошибка");
    }
}
