package models;

import java.util.Objects;

public class InternalTransferData {
    private final String sourceAccount;
    private final String receiverAccount;
    private final long amount;
    private final String description;

    public InternalTransferData(String sourceAccount, String receiverAccount, long amount, String description) {
        this.sourceAccount = sourceAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.description = description;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public long getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        // Return true if both references point to the same object in memory
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        // Cast generic Object to InternalTransferData > to access field
        InternalTransferData that = (InternalTransferData) obj;

        return amount == that.amount
                && Objects.equals(sourceAccount, that.sourceAccount)
                && Objects.equals(receiverAccount, that.receiverAccount)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                sourceAccount,
                receiverAccount,
                amount,
                description
        );
    }

    @Override
    public String toString() {
        return "InternalTransferData{" +
                "sourceAccount='" + sourceAccount + '\'' +
                ", receiverAccount='" + receiverAccount + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
