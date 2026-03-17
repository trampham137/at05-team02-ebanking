package enums;

public enum Branch {
    DANANG("Chi nhánh Đà Nẵng"),
    HANOI("Chi nhánh Hà Nội"),
    SAIGON("Chi nhánh Sài Gòn"),
    QUANGNAM("Chi nhánh Quảng Nam"),
    CANTHO("Chi nhánh Cần Thơ"),
    PHUTHO("Chi nhánh Phú Thọ");
    private final String value;

    Branch(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
