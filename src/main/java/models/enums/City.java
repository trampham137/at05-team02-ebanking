package models.enums;

public enum City {
    DA_NANG("Thanh Pho Da Nang"),
    QUANG_NAM("Quang Nam");

    private final String displayName;

    City(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
