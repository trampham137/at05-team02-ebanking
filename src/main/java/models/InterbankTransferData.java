package models;

import enums.Bank;
import enums.Branch;

public class InterbankTransferData {
    private final String sourceAccount;
    private final String receiverAccount;
    private final String receiverName;
    private final String bank;
    private final String branch;
    private final String description;
    private final String amount;

    public InterbankTransferData(String sourceAccount, String receiverAccount, String receiverName, String bank, String branch, String description, String amount) {
        this.sourceAccount = sourceAccount;
        this.receiverAccount = receiverAccount;
        this.receiverName = receiverName;
        this.bank = bank;
        this.branch = branch;
        this.description = description;
        this.amount = amount;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getBank() {
        return bank;
    }

    public String getBranch() {
        return branch;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }
}
