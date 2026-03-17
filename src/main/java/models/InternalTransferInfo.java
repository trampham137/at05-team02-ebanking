package models;

public class InternalTransferInfo {
    public int sourceAccount;
    public int receiverAccount;
    public double amount;
    public String transferDescription;

    public InternalTransferInfo(int sourceAccount, int receiverAccount, double amount, String transferDescription) {
        this.sourceAccount = sourceAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.transferDescription = transferDescription;
    }
}
