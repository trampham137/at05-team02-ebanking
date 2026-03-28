package models;

public class DepositData {
    private final String receiverAccount;
    private final long amount;
    private final String description;

    public DepositData(String receiverAccount, long amount, String description) {
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.description = description;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public Long getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
