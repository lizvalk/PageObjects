package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement headingTransfer = $("h1, heading ");
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement toField = $("[data-test-id='to'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");

    public TransferPage() {
        headingTransfer.shouldBe(Condition.visible).shouldBe(Condition.exactText("Пополнение карты"));
    }
    public void transferMoney(String amount, DataHelper.CardInfo cardInfo) {
        amountField.setValue(amount);
        fromField.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }
    public DashboardPage successfulMoneyTransfer(String amount, DataHelper.CardInfo cardInfo) {
        transferMoney(amount, cardInfo);
        return new DashboardPage();
    }
    public void ErrorMessage(String text) {
        errorMessage.shouldBe(Condition.exactText(text), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}

