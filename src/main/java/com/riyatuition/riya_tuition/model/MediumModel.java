package com.riyatuition.riya_tuition.model;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "createdDate", "modifiedDate" })

public class MediumModel {
	private Integer id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
   
}
