package com.douyasi.tinyme.common.util;


/**
 * ValidateUtil
 * 
 * Code from `https://github.com/shalousun/ApplicationPower/blob/master/Common-util/src/main/java/com/power/common/util/ValidateUtil.java`
 * 
 * @author raoyc
 */
public class ValidateUtil {

    /**
     * Pattern for email
     */
    public static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    /**
     * Pattern for Chinese
     */
    public static final String CHINESE_PATTERN = "^[\u4e00-\u9fa5]{0,}$";

    /**
     * Patterns for English letters or/and Arabic numbers
     */
    public static final String NUMBER_ADN_LETTER = "^[A-Za-z0-9]+$";
    public static final String NUMBER_PATTERN = "[0-9]+";
    public static final String LETTER_PATTERN = "[a-zA-Z]+";
    
    /**
     * Patterns for mobile phone, fixed-line telephone, 400-service-phone in mainland China
     */
    public static final String PHONE_PATTERN = "^13[0-9]{9}|14[579]{1}[0-9]{8}|15[012356789]{1}[0-9]{8}|17[0135678]{1}[0-9]{8}|18[0-9]{9}$";
    public static final String TELEPHONE_PATTERN = "^(0\\d{2,3}-)?(\\d{7,8})(-(\\d{3,}))?$";
    public static final String TELEPHONE_400_PATTERN = "((400)(\\d{7}))|((400)-(\\d{3})-(\\d{4}))";

    /**
     * Patterns for some special string
     */
    public static final String USERNAME_PATTERN = "^[A-Za-z0-9_]{3,15}$";
    public static final String PASSWORD_PATTERN = "^(?![0-9]+$)[0-9A-Za-z]{6,20}$";
    public static final String UUID_PATTERN = "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}";
    public static final String IPV4_PATTERN = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
    public static final String IPV6_PATTERN = "([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)";

    /**
     * Patterns for integer or/and float numbers 
     */
    public static final String INT_OR_FLOAT_PATTERN = "^\\d+\\.\\d+|\\d+$";
    public static final String FLOAT_PATTERN = "^(-?\\d+)(\\.\\d+)?$";

    /**
     * Checks if a CharSequence is empty or null.
     *
     * @param str String
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim()) || "null".equals(str.trim()) || "NaN".equals(str.trim());
    }

    /**
     * Negative to function `isEmpty`
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Match strings based on regular expressions
     * 
     * @param str     String
     * @param pattern regular expressions
     * @return boolean
     */
    public static boolean validate(String str, String pattern) {
        return isNotEmpty(str) && str.matches(pattern);
    }

    /**
     * Check if the string is a email
     * 
     * @param email Email
     * @return boolean
     */
    public static boolean isEmail(String email) {
        return validate(email, EMAIL_PATTERN);
    }

    /**
     * Check if the string is in Chinese
     *
     * @param chineseStr chineseStr
     * @return boolean
     */
    public static boolean isChinese(String chineseStr) {
        return validate(chineseStr, CHINESE_PATTERN);
    }
    

    /**
     * Verify numbers or letters
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNumberLetter(String str) {
        return validate(str, NUMBER_ADN_LETTER);
    }

    /**
     * Verify numbers
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNumber(String str) {
        return validate(str, NUMBER_PATTERN);
    }

    /**
     * Verify letters
     *
     * @param str String
     * @return boolean
     */
    public static boolean isLetter(String str) {
        return validate(str, LETTER_PATTERN);
    }
    

    /**
     * Validate Chinese mobile phone number
     *
     * @param phone String
     * @return boolean
     */
    public static boolean isPhone(String phone) {
        return validate(phone, PHONE_PATTERN);
    }
    
    /**
     * Verify Chinese fixed-line telephone number
     *
     * @param phoneNumber String
     * @return boolean
     */
    public static boolean isTelephone(String phoneNumber) {
        return validate(phoneNumber, TELEPHONE_PATTERN) || validate(phoneNumber, TELEPHONE_400_PATTERN);
    }

    /**
     * Validate username
     *
     * @param str String
     * @return boolean
     */
    public static boolean isUserName(String str) {
        return validate(str, USERNAME_PATTERN);
    }

    /**
     * Validate password
     * 
     * @param str password
     * @return boolean
     */
    public static boolean isPassword(String str) {
        return validate(str, PASSWORD_PATTERN);
    }

    /**
     * Verify that it is a valid uuid,
     * If you remove the "-" you must also have a 32-bit length.
     * 
     * @param str String
     * @return boolean
     */
    public static boolean isUuid(String str) {
        return validate(str, UUID_PATTERN) || validate(str, "[0-9a-z]{32}");
    }

    /**
     * Verify ip address is legal, including ipv4 and ipv6
     *
     * @param ip ip address
     * @return boolean
     */
    public static boolean isIp(String ip) {
        if (isEmpty(ip)) {
            return false;
        } else {
            ip = ip.toLowerCase();
            return validate(ip, IPV4_PATTERN) || validate(ip, IPV6_PATTERN);
        }
    }

    /**
     * Verify that it is a valid integer or float number
     *
     * @param number String number
     * @return boolean
     */
    public static boolean isIntOrFloat(String number) {
        return validate(number, INT_OR_FLOAT_PATTERN);
    }

    /**
     * Verify that it is a valid float number
     *
     * @param number String number
     * @return boolean
     */
    public static boolean isFloat(String number) {
        return validate(number, FLOAT_PATTERN);
    }
    
}
