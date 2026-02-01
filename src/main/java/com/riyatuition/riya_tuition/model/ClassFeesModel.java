package com.riyatuition.riya_tuition.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "id", "standardId", "standardName" ,"amount", "createdDate", "modifiedDate" })

public class ClassFeesModel {

	private Integer id;
	
    private Integer standardId;
    
    private String standardName;

    private BigDecimal amount;
    
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
