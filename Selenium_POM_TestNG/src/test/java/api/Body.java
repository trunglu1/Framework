package api;

public class Body {
    public static String setCellValue(String sheetName, String columnName, String rowIndex, String setValue) {
        return String.format("{\"range\": \"%s!%s%s\",\"values\": [[\"%s\"]],\"majorDimension\": \"ROWS\"}",
                sheetName, columnName, rowIndex, setValue);
    }
}
