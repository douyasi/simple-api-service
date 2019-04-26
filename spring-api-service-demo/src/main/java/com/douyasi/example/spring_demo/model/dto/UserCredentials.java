package com.douyasi.example.spring_demo.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class UserCredentials {
    private String email;
    private String password;
}
