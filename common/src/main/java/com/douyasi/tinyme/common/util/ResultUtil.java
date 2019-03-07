package com.douyasi.tinyme.common.util;

import com.douyasi.tinyme.common.constants.BaseErrorCode;
import com.douyasi.tinyme.common.model.CommonResult;

/**
 * ResultUtil
 * 
 * @author raoyc
 */
public class ResultUtil {
    
    /**
     * return success
     *
     * @param data
     * @return
     */
    public static <T> CommonResult<T> returnSuccess(T data) {
        CommonResult result = new CommonResult(BaseErrorCode.Common.SUCCESS.getCode(), BaseErrorCode.Common.SUCCESS.getMessage());
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * return error
     *
     * @param msg error message
     * @param code 
     * @return
     */
    public static CommonResult returnError(String msg, String code) {
        CommonResult result = new CommonResult();
        if (ValidateUtil.isEmpty(code)) {
            code = BaseErrorCode.Common.FAIL.getCode();
        }
        result.setCode(code);
        result.setData("");
        result.setMessage(msg);
        return result;
    }
}
