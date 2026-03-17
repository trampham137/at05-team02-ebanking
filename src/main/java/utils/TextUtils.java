package utils;

import java.text.Normalizer;

public class TextUtils {
    private TextUtils() {
    }

    public static String normalizeUiText(String text) {
        if (text == null) {
            return "";
        }

        return Normalizer.normalize(text, Normalizer.Form.NFC)
                .replace('\u00A0', ' ')
                .replaceAll("\\s+", " ")
                .trim();
    }
}
