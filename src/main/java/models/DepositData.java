package models;

public class DepositData {
    private final String receiverAccount;
    private final String amount;
    private final String description;

    public DepositData(String receiverAccount, String amount, String description) {
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.description = description;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
