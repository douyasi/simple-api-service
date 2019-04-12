package com.douyasi.tinyme.common.util;

import com.douyasi.tinyme.common.constants.BaseErrorCode;
import com.douyasi.tinyme.common.interfaces.IMessage;
import com.douyasi.tinyme.common.model.CommonResult;
import com.douyasi.tinyme.common.model.EmptyData;

/**
 * ResultUtil
 * 
 * @author raoyc
 */
public class ResultUtil {
    
    /**
     * return success with data
     *
     * @param data
     * @return
     */
    public static <T> CommonResult<T> returnSuccess(T data) {
        CommonResult<T> result = new CommonResult<T>(BaseErrorCode.Common.SUCCESS.getCode(), BaseErrorCode.Common.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }
    
    /**
     * return success without data
     *
     * @param data
     * @return
     */
    public static <T> CommonResult<T> returnSuccess() {
        CommonResult<T> result = new CommonResult<T>(BaseErrorCode.Common.SUCCESS.getCode(), BaseErrorCode.Common.SUCCESS.getMessage());
        EmptyData emptyData = new EmptyData();
        result.setData(emptyData);
        return result;
    }

    /**
     * return error with code & message
     *
     * @param msg error message
     * @param code error code
     * @return
     */
    public static <T> CommonResult<T> returnError(String msg, String code) {
        if (ValidateUtil.isEmpty(code)) {
            code = BaseErrorCode.Common.FAIL.getCode();
        }
        CommonResult<T> result = CommonResult.fail(code, msg);
        return result;
    }
    
    /**
     * return error using IMessage
     * 
     * @param message
     * @return
     */
    public static <T> CommonResult<T> returnError(IMessage message) {
        CommonResult<T> result = CommonResult.fail(message);
        return result;
    }
}
