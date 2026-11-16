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

    static void digitsOnly(String value, String field) {
        if (value == null || !value.matches("[0-9]+")) {
            throw new IllegalArgumentException(field + " chỉ được chứa chữ số");
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