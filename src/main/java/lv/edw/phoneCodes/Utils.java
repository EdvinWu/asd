package lv.edw.phoneCodes;

import org.apache.commons.lang3.StringUtils;

public class Utils {
    public static final String WIKI_PHONE_CODES_URL = "https://en.wikipedia.org/wiki/List_of_country_calling_codes";

    public static final String INVALID_COUNTRY_CODE_ERROR_MSG = "Looks like country code you've entered is invalid";
    public static final String NUMBER_TOO_LONG_ERROR_MSG = "Number you've entered is too long";
    public static final String NOT_A_NUMBER_ERROR_MSG = "Number you've entered contains characters";
    public static final String NUMBER_TOO_SHORT_ERROR_MSG = "Number you've entered is too short";

    public static String removeWikiSpecificCharacters(String str) {
        if (str != null) {
            String s = StringUtils.deleteWhitespace(str).replaceAll(",", "");
            if (s.contains("[")) {
                int i = s.indexOf("[");
                return s.substring(0, i);
            }
            return s;
        }
        return null;
    }

}
