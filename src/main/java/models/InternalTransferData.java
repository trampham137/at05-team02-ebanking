package models;

public class InternalTransferData {
    private final String sourceAccount;
    private final String receiverAccount;
    private final long amount;
    private final String description;

    public InternalTransferData(String sourceAccount, String receiverAccount, long amount, String description) {
        this.sourceAccount = sourceAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.description = description;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public long getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
