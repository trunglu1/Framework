package api;

public class Body {
    public static String setCellValue(String sheetName, String range, String valueRange, String dimension) {
        return String.format("{\"range\": \"%s!%s\",\"values\": [[%s]],\"majorDimension\": \"%s\"}",
                sheetName, range, valueRange, dimension.toUpperCase());
    }
}
