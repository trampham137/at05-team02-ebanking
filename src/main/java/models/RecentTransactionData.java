package models;

import java.text.Normalizer;
import java.time.LocalDateTime;

public class RecentTransactionData {
    private final String dateTimeText;
    private final LocalDateTime dateTime;
    private final String accountNumber;
    private final String amountText;

    public RecentTransactionData(String dateTimeText, LocalDateTime dateTime, String accountNumber, String amountText) {
        this.dateTimeText = dateTimeText;
        this.dateTime = dateTime;
        this.accountNumber = accountNumber;
        this.amountText = amountText;
    }

    public String getDateTimeText() {
        return dateTimeText;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAmountText() {
        return amountText;
    }

    public long getAmountValue() {
        String normalized = Normalizer.normalize(amountText, Normalizer.Form.NFC)
                .replace("VNĐ", "")
                .replace("VND", "")
                .replace(",", "")
                .replace(".", "")
                .replace(" ", "")
                .trim();

        return Long.parseLong(normalized);
    }
}
