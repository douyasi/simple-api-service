package com.douyasi.example.spring_demo.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Page Entity
 * @author raoyc
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public class Page {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Long id, uid;

    private String content;

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
