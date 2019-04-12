package com.douyasi.tinyme.common.model;

import java.io.Serializable;

/**
 * BaseResult
 * 
 * @author raoyc
 */
public abstract class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = -2335839219066407260L;

    /**
     * error code
     */
    private String code;

    /**
     * message to response
     */
    private String message;

    /**
     * data to response
     */
    private T data;

    /**
     * Current time stamp
     */
    private String timestamp;

    /**
     * getCode
     * 
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * setCode
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * getMessage
     * 
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * setMessage
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * getData
     * 
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     * setData
     * 
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * getTimestamp
     * 
     * @return
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * setTimestamp
     * 
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
