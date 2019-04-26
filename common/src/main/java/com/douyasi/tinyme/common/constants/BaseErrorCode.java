package com.douyasi.tinyme.common.constants;

import com.douyasi.tinyme.common.interfaces.IMessage;

/**
 * BaseErrorCode
 * 
 * @author raoyc
 */
public interface BaseErrorCode {

    enum Common implements IMessage {
        /**
         * success
         */
        SUCCESS("20000", "ok"),

        /**
         * param empty
         */
        PARAM_EMPTY("30001", "param invalid"),

        /**
         * param error
         */
        PARAM_ERROR("30002", "error param"),

        /**
         * fail
         */
        FAIL("50000", "internal error"),

        /**
         * unknown error
         */
        UNKNOWN_ERROR("99999", "system busy, try later...");

        private String code;
        private String message;


        Common(String errCode, String errMsg) {
            this.code = errCode;
            this.message = errMsg;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }
}