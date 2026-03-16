package enums;

public enum AccountType {
    NON_TERM("Tài Khoản kỳ gửi không kỳ hạn"),
    SAVING("Tài khoản tiết kiệm");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
