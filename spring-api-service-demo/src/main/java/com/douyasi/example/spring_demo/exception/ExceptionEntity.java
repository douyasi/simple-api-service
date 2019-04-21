package com.douyasi.example.spring_demo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class ExceptionEntity implements Serializable {

    private static final long serialVersionUID = 9031619656203165441L;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String code, message, path, error, timestamp;

    public String getTimestamp() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        return now.format(formatter);
    }
}
