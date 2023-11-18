package com.ds.Assignement1.Assignement1.Dto;

import lombok.Data;

@Data
public class UpdateDTO {
    private Long id;
    private String field;
    private Object value;
}