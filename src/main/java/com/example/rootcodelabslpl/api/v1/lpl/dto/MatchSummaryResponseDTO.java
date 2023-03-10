package com.example.rootcodelabslpl.api.v1.lpl.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Data
@Component
public class MatchSummaryResponseDTO {
    private String winningTeamName;
    private HashMap<String, Integer> scoresOfEachTeam;
    private HashMap<String, Double> playedOverInEachInnings;
    private HashMap<String, String> mostRunsScorer;
    private HashMap<String, String> mostWicketsTaker;
}
