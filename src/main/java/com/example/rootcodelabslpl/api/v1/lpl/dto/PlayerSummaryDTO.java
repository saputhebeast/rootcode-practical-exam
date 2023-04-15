package com.example.rootcodelabslpl.api.v1.lpl.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Data
@Component
public class PlayerSummaryDTO {
    private String name;
    private String type;
    private HashMap<String, String> runsDetails;

    private int wickets;
}
