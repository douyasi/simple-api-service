package com.douyasi.tinyme.common.util;

import java.util.UUID;

/**
 * UuidUtil
 *
 * @author raoyc
 */
public class UuidUtil {

    /**
     * Get original UUID (with '-' exploded)
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 32 length UUID (without '-' exploded)
     *
     * @return
    */
    public static String id() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
