package models.enums;

public enum AccountType {
    CURRENT_ACCOUNT("Tài Khoản kỳ gửi không kỳ hạn"),
    SAVINGS_ACCOUNT("Tài Khoản tiết kiệm");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
