package com.douyasi.tinyme.common.model;


import com.douyasi.tinyme.common.constants.BaseErrorCode;
import com.douyasi.tinyme.common.interfaces.IMessage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * CommonResult
 *
 * @author raoyc
 */
@XmlRootElement
public class CommonResult<T> extends BaseResult implements Serializable {

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
     * CommonResult #1
     * 
     * @param success
     * @param message
     */
    public CommonResult(boolean success, String message) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setTimestamp(long2TimeStr());
    }

    /**
     * CommonResult #2
     * 
     * @param success
     */
    public CommonResult(boolean success) {
        this.setSuccess(success);
    }

    /**
     * CommonResult #3
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
     * CommonResult #4
     * 
     * @param success
     * @param message
     * @param data
     */
    public CommonResult(boolean success, String message, String data) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setData(data);
        this.setTimestamp(long2TimeStr());
    }

    /**
     * Usage:
     * Result.ok().setResult("hello")
     * 
     * Return with data
     * 
     * @param data
     * @return CommonResult
     */
    public CommonResult setResult(String data) {
        this.setData(data);
        this.setTimestamp(long2TimeStr());
        return this;
    }

    /**
     * Success Response
     *
     * @return CommonResult
     */
    public static CommonResult ok() {
        return ok(BaseErrorCode.Common.SUCCESS);
    }

    /**
     * User-defined Message response, using IMessage Enum
     * 
     * @param msg IMessage interface
     * @param <T> Object
     * @return CommonResult
     */
    public static <T> CommonResult<T> ok(IMessage msg) {
        return baseCreate(msg.getCode(), msg.getMessage(), true);
    }

    /**
     * Fail Response
     *
     * @return CommonResult
     */
    public static CommonResult fail() {
        return fail(BaseErrorCode.Common.UNKNOWN_ERROR);
    }

    /**
     * Return fail message
     *
     * @param message IMessage
     * @return CommonResult
     */
    public static CommonResult fail(IMessage message) {
        return fail(message.getCode(), message.getMessage());
    }

    /**
     * Return fail code and message
     *
     * @param code
     * @param msg
     * @return CommonResult
     */
    public static CommonResult fail(String code, String msg) {
        return baseCreate(code, msg, false);
    }

    /**
     * baseCreate
     * 
     * @param code
     * @param msg
     * @param success
     * @param <T>
     * @return
     */
    private static <T> CommonResult<T> baseCreate(String code, String msg, boolean success) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setSuccess(success);
        result.setMessage(msg);
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
