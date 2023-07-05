package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private ElementsCollection actionButton = $$("[data-test-id='action-deposit']");
    public DashboardPage() {
        heading.shouldBe(Condition.visible).shouldBe(Condition.exactText("Личный кабинет"));
    }
    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        val text = cards.findBy(Condition.text(cardInfo.getCardNumber().substring(15))).getText();
        return extractBalance(text);
    }
    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
    public TransferPage selectFirstCardToTransfer() {
        actionButton.first().click();
        return new TransferPage();
    }
    public TransferPage selectSecondCardToTransfer() {
        actionButton.last().click();
        return new TransferPage();
    }
}
