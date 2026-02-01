package com.riyatuition.riya_tuition.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id","name","email","phone","image"})
public class AdminLoginResponse {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String image;
    
}
