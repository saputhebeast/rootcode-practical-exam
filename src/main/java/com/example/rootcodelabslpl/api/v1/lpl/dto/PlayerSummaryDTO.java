package com.example.rootcodelabslpl.api.v1.lpl.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PlayerSummaryDTO {
    private String name;
    private String type;
    private int runs;
    private int wickets;
}
