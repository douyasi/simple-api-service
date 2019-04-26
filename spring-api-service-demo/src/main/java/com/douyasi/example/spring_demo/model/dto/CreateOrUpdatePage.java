package com.douyasi.example.spring_demo.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
@NoArgsConstructor
@Getter
@Setter
@ToString
*/
public class CreateOrUpdatePage {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}