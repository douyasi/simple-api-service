package com.douyasi.tinyme.common.util;

import javax.servlet.http.HttpServletRequest;


/**
 * IPUtil
 *
 * Code from `https://github.com/Javen205/IJPay/blob/master/src/main/java/com/jpay/ext/kit/IpKit.java`
 * and `https://www.cnblogs.com/Mauno/p/Mauno.html`
 *
 * @author raoyc
 */
public class IpUtil {
    
    private static String UNKNOWN_STR = "unknown";

    /**
     * Get user-client IP in some cases
     *
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        // X-Forwarded-For : Squid or Proxy server
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || UNKNOWN_STR.equalsIgnoreCase(ip)) {
            // X-Real-IP : Nginx server
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_STR.equalsIgnoreCase(ip)) {
            // Proxy-Client-IP : Apache server
            ip = request.getHeader("Proxy-Client-IP");
        }
        
        if (ip == null || ip.length() == 0 || UNKNOWN_STR.equalsIgnoreCase(ip)) {
            // WL-Proxy-Client-IP : Weblogic server
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        
        if (ip == null || ip.length() == 0 || UNKNOWN_STR.equalsIgnoreCase(ip)) {
            // HTTP_CLIENT_IP ：Some server
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        
        if (ip == null || ip.length() == 0 || UNKNOWN_STR.equalsIgnoreCase(ip)) {
            // PROXY_FORWARDED_FOR : Some Proxy server
            ip = request.getHeader("PROXY_FORWARDED_FOR");
        }
        if (ip != null && ip.length() != 0) {
            // Some network server may bring multi-ip-address (exploded by ',') , select the 1st item as client ip
            ip = ip.split(",")[0];
        }
        
        if (ip == null || ip.length() == 0 || UNKNOWN_STR.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
  
}
