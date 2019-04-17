package com.douyasi.example.spring_demo.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * UserCredentials for `postLogin` body
 * 
 * @author raoyc
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserCredentials {
    private String email;
    private String password;
}
