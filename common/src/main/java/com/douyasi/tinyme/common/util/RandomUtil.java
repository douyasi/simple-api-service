package com.douyasi.tinyme.common.util;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * RandomUtil
 * 
 * Code from `https://github.com/shalousun/ApplicationPower/blob/master/Common-util/src/main/java/com/power/common/util/RandomUtil.java`
 * 
 * @author raoyc
 */
public class RandomUtil {

    private static Random random = new Random();

    /** random selected numbers */
    private static final String BASE_NUMBER = "0123456789";
    
    /** random selected characters */
    private static final String BASE_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    /** random selected characters and numbers */
    private static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;
    
    /**
     * random int value between min and max
     * 
     * @param min min value
     * @param max max value
     * @return int value
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    /**
     * random int value between o and limit
     * 
     * @param limit limit of max value
     * @return int value
     */
    public static int randomInt(int limit) {
        return random.nextInt(limit);
    }
    /**
     * random int value
     * 
     * @return int value
     */
    public static int randomInt() {
        return random.nextInt();
    }

    /**
     * random long
     * 
     * @return long value
     */
    public static long randomLong(){
        return random.nextLong();
    }

    /**
     * random long value between min and max
     * 
     * @param min min value
     * @param max max value
     * @return long value
     */
    public static long randomLong(long min,long max){
        long rangeLong = min + (((long) (new Random().nextDouble() * (max - min))));
        return rangeLong;
    }

    /**
     * random string that only contains numbers and letters
     * 
     * @param length length of String
     * @return random string
     */
    public static String randomString(int length) {
        return randomString(BASE_CHAR_NUMBER, length);
    }

    /**
     * random string that only contains numbers
     * 
     * @param length length of String
     * @return random string
     */
    public static String randomNumbers(int length) {
        return randomString(BASE_NUMBER, length);
    }


    /**
     * random string from base characters
     * 
     * @param baseString base characters
     * @param length length of String
     * @return random string
     */
    public static String randomString(String baseString, int length) {
        StringBuffer sb = new StringBuffer();

        if (length < 1) {
            length = 1;
        }
        int baseLength = baseString.length();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }

    /**
     * random double value between min and max
     * 
     * @param min min value
     * @param max max value
     * @return double
     */
    public static double randomDouble(final double min, final double max) {
        return min + ((max - min) * random.nextDouble());
    }

    /**
     * random double value between 0 and 100
     * 
     * @return double
     */
    public static double randomDouble(){
        return randomDouble(0.00, 100.00);
    }

    /**
     * random double value between 0 and 100
     * 
     * @param format number format
     * @return String
     */
    public static String randomDouble(String format) {
        return new DecimalFormat(format).format(randomDouble());
    }

}
