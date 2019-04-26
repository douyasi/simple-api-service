package com.douyasi.tinyme.common.model;


import com.douyasi.tinyme.common.constants.BaseErrorCode;
import com.douyasi.tinyme.common.interfaces.IMessage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CommonResult
 *
 * @author raoyc
 */
public class CommonResult<T> extends BaseResult<Object> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7268040542410707954L;


    /**
     * Construct
     */
    public CommonResult() {

    }

    /**
     * CommonResult message
     *
     * @param message
     */
    public CommonResult(String message) {
        this.setMessage(message);
        this.setTimestamp(long2TimeStr());
    }

    /**
     * CommonResult code & message
     * 
     * @param code
     * @param message
     */
    public CommonResult(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
        this.setTimestamp(long2TimeStr());
    }

    /**
     * setResult
     * 
     * @param data
     * @return CommonResult
     */
    public CommonResult<T> setResult(T data) {
        this.setData(data);
        this.setTimestamp(long2TimeStr());
        return this;
    }

    /**
     * Success Response
     *
     * @return CommonResult
     */
    public static <T> CommonResult<T> ok() {
        return ok(BaseErrorCode.Common.SUCCESS);
    }

    /**
     * User-defined Message response, using IMessage Enum
     * 
     * @param msg IMessage interface
     * @return CommonResult
     */
    public static <T> CommonResult<T> ok(IMessage msg) {
        return baseCreate(msg.getCode(), msg.getMessage());
    }

    /**
     * Fail Response
     *
     * @return CommonResult
     */
    public static <T> CommonResult<T> fail() {
        return fail(BaseErrorCode.Common.UNKNOWN_ERROR);
    }

    /**
     * Return fail message
     *
     * @param message IMessage
     * @return CommonResult
     */
    public static <T> CommonResult<T> fail(IMessage message) {
        return fail(message.getCode(), message.getMessage());
    }

    /**
     * Return fail code and message
     *
     * @param code
     * @param msg
     * @return CommonResult
     */
    public static <T> CommonResult<T> fail(String code, String msg) {
        return baseCreate(code, msg);
    }

    /**
     * baseCreate
     * 
     * @param code
     * @param msg
     * @return CommonResult
     */
    private static <T> CommonResult<T> baseCreate(String code, String msg) {
        CommonResult<T> result = new CommonResult<T>();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(new EmptyData());
        result.setTimestamp(long2TimeStr());
        return result;
    }

    /**
     * long2TimeStr
     * 
     * @return
     */
    private static String long2TimeStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = df.format(new Date());
        return dateTime;
    }
}
