package com.riyatuition.riya_tuition.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"email","password"})
public class AdminLoginModel {
    private String email;
    private String password;
}
