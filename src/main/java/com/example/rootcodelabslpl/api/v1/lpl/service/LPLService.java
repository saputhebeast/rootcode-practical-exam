package com.example.rootcodelabslpl.api.v1.lpl.service;
import com.example.rootcodelabslpl.api.v1.lpl.model.LPL;

import java.util.HashMap;
import java.util.List;

public interface LPLService {
    public List<LPL> saveGameDetails(List<LPL> lpl);
    public String winningTeam();
    public HashMap<String, Integer> scoresOfTeams();
    public HashMap<String, Double> playedOverInEachInnings();
    public HashMap<String, String> mostRunScorer();
    public HashMap<String, String> highestWicketTaker();

    boolean isPlayerExist(String name);

    String matchingPlayerName(String name);

    String playerType(String name);

    int wicketsForPlayer(String name);

    int runsForPlayer(String name);
}
