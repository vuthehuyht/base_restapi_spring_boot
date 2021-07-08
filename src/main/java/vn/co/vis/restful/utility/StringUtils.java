package vn.co.vis.restful.utility;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Giang Thanh Quang
 * @since 10/16/2020
 */
public class StringUtils {
    public static String snakeCaseToCamelCase(String strInput) {
        StringTokenizer token = new StringTokenizer(strInput, "_");
        StringBuilder str = new StringBuilder(token.nextToken());
        while (token.hasMoreTokens()) {
            String s = token.nextToken();
            str.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
        }
        return str.toString();
    }

    /**
     * Convert a camel case String to snake case
     *
     * @param strInput String input
     * @return snake case string
     */
    public static String camelCaseToSnakeCase(String strInput) {
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(strInput);
        String result = m.replaceAll(match -> "_" + match.group().toLowerCase());
        return result;
    }

    /**
     * Convert a camel case String to snake case for number
     *
     * @param strInput String input
     * @return snake case number string
     */
    public static String camelCaseToSnakeCaseNumber(String strInput) {
        Matcher m = Pattern.compile("(?<=[a-z0-9])[A-Z]").matcher(strInput);
        String result = m.replaceAll(match -> "_" + match.group().toLowerCase());
        return result;
    }
}
