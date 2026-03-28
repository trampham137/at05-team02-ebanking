package models;

public class OtpEmailData {
    private final String subject;
    private final String body;
    private final String otpCode;

    public OtpEmailData(String subject, String body, String otpCode) {
        this.subject = subject;
        this.body = body;
        this.otpCode = otpCode;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getOtpCode() {
        return otpCode;
    }
}
