package com.douyasi.tinyme.common.util;

import com.douyasi.tinyme.common.constants.BaseErrorCode;
import com.douyasi.tinyme.common.model.CommonResult;
import com.douyasi.tinyme.common.model.EmptyData;

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
        CommonResult<T> result = new CommonResult<T>(BaseErrorCode.Common.SUCCESS.getCode(), BaseErrorCode.Common.SUCCESS.getMessage());
        if (data == null) {
            EmptyData emptyData = new EmptyData();
            result.setData(emptyData);
        } else {
            result.setData(data);
        }
        return result;
    }

    /**
     * return error
     *
     * @param msg error message
     * @param code 
     * @return
     */
    public static <T> CommonResult<T> returnError(String msg, String code) {
        if (ValidateUtil.isEmpty(code)) {
            code = BaseErrorCode.Common.FAIL.getCode();
        }
        CommonResult<T> result = CommonResult.fail(code, msg);
        return result;
    }
}
