package com.douyasi.example.spring_demo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Token Entity
 * @author raoyc
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public class Token implements Serializable {

    private static final long serialVersionUID = 1786836742703432189L;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Long /*id,*/ uid, expiredAt;

    private String token;

    private LocalDateTime createdAt, updatedAt;

    public String getCreatedAt() {
        return createdAt.format(formatter);
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt.format(formatter);
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
