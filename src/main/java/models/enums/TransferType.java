package models.enums;

public enum TransferType {
    INTERNAL(1100),
    INTERBANK(3300);

    private final long fee;

    TransferType(long fee) {
        this.fee = fee;
    }

    public long getFee() {
        return fee;
    }
}
