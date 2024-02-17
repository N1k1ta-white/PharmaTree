package bg.sofia.uni.fmi.mjt.pharmatree.api.util;

public class CsvSeparator {
    private static final String SEPARATOR = ";";
    private static final String ARRAY_SEPARATOR = ",";

    public static String getSeparator() {
        return SEPARATOR;
    }

    public static String getArraySeparator() {
        return ARRAY_SEPARATOR;
    }
}
