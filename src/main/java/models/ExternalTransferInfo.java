package models;

import enums.Bank;
import enums.Branch;

public class ExternalTransferInfo {
    public int sourceAccount;
    public int receiverAccount;
    public String receiverName;
    public Bank bank;
    public Branch branch;
    public String transferDescription;
    public double amount;
}
