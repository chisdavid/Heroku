package com.ds.Assignement1.Assignement1.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramStartTimeAndBaseLine {
    private Integer startTime ;
    private List<RPCResponse> baseline;
}
