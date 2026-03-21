package utils;

public final class NumberUtils {
    private NumberUtils() {
    }

    public static long parseLong(String value) {
        return Long.parseLong(value);
    }

    public static boolean isNumeric(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
