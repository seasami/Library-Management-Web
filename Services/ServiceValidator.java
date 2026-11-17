package Services;

final class ServiceValidator {
    private ServiceValidator() {
    }

    static void requireText(String value, String field, int maxLength) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " không được để trống");
        }
        if (value.length() > maxLength) {
            throw new IllegalArgumentException(
                    field + " không được vượt quá " + maxLength + " ký tự");
        }
    }

    static void optionalText(String value, String field, int maxLength) {
        if (value != null) {
            requireText(value, field, maxLength);
        }
    }

    static void email(String value, String field) {
        String pattern = "^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+"
                + "(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
                + "(?:[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?\\.)+"
                + "[A-Za-z]{2,63}$";
        if (value == null || value.length() > 254 || !value.matches(pattern)) {
            throw new IllegalArgumentException(field + " không đúng định dạng email");
        }
    }

    static void digitsWithLeadingZero(String value, String field, int length) {
        if (value == null || !value.matches("0[0-9]{" + (length - 1) + "}")) {
            throw new IllegalArgumentException(field + " không đúng định dạng");
        }
    }

    static void integerRange(Integer value, int minimum, int maximum, String field) {
        if (value != null && (value < minimum || value > maximum)) {
            throw new IllegalArgumentException(
                    field + " phải nằm trong khoảng " + minimum + " đến " + maximum);
        }
    }

    static void oneOf(String value, String field, String... allowedValues) {
        for (String allowedValue : allowedValues) {
            if (allowedValue.equals(value)) {
                return;
            }
        }
        throw new IllegalArgumentException(
                field + " chỉ nhận: " + String.join(", ", allowedValues));
    }
}