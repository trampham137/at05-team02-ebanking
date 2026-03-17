package enums;

public enum Bank {
    DONGA("Ngân hàng Đông Á"),
    VIETINBANK("Ngân hàng VietinBank"),
    VIETCOMBANK("Ngân hàng VietComeBank"),
    ACB("Ngân hàng ACB");
    private final String value;

    Bank(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
