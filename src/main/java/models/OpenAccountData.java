package models;

import models.enums.AccountType;

public class OpenAccountData {
    private final AccountType accountType;

    public OpenAccountData(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
