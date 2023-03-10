package com.example.rootcodelabslpl.api.v1.lpl.service.impl;

import com.example.rootcodelabslpl.api.v1.lpl.model.LPL;
import com.example.rootcodelabslpl.api.v1.lpl.repository.LPLRepository;
import com.example.rootcodelabslpl.api.v1.lpl.service.LPLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LPLServiceImpl implements LPLService {
    @Autowired
    private LPLRepository lplRepository;

    @Override
    public List<LPL> saveGameDetails(List<LPL> lpl) {
        return lplRepository.saveAll(lpl);
    }

    @Override
    public String winningTeam(){
        return lplRepository.getTotalTeamScore("Galle Gladiators") > lplRepository.getTotalTeamScore("Jaffna Kings") ? "Galle Gladiators" : "Jaffna Kings";
    }

    @Override
    public HashMap<String, Integer> scoresOfTeams(){
        HashMap<String, Integer> scores = new HashMap<>();
        scores.put("Galle Gladiators", lplRepository.getTotalTeamScore("Galle Gladiators"));
        scores.put("Jaffna Kings", lplRepository.getTotalTeamScore("Jaffna Kings"));
        return scores;
    }

    @Override
    public HashMap<String, Double> playedOverInEachInnings(){
        HashMap<String, Double> overs = new HashMap<>();
        overs.put("Inning 1", lplRepository.getBalledOversFromAnInning(1));
        overs.put("Inning 2", lplRepository.getBalledOversFromAnInning(2));
        return overs;
    }

    @Override
    public HashMap<String, String> mostRunScorer(){
        HashMap<String, String> scorerData = new HashMap<>();
        scorerData.put("Name", lplRepository.topRunScorerName());
        scorerData.put("Score", lplRepository.topRunScorerScore());
        return scorerData;
    }

    @Override
    public HashMap<String, String> highestWicketTaker(){
        HashMap<String, String> wicketTakerData = new HashMap<>();
        wicketTakerData.put("Name", lplRepository.highestWicketTakerName());
        wicketTakerData.put("Wickets", lplRepository.highestWicketTakerTakenWickets());
        return wicketTakerData;
    }

    @Override
    public boolean isPlayerExist(String name){
        return lplRepository.isPlayerBatsman(name) != null || lplRepository.isPlayerBowler(name) != null;
    }

    @Override
    public String matchingPlayerName(String name){
        if(lplRepository.isPlayerBatsman(name) != null || lplRepository.isPlayerBowler(name) != null)
            return lplRepository.isPlayerBatsman(name);
        return null;
    }

    @Override
    public String playerType(String name){
        if(lplRepository.isPlayerBatsman(name) != null && lplRepository.isPlayerBowler(name) != null){
            return "All-rounder";
        }else if(lplRepository.isPlayerBatsman(name) != null){
            return "Batsman";
        }else if(lplRepository.isPlayerBowler(name) != null){
            return "Bowler";
        }
        return null;
    }

    @Override
    public int wicketsForPlayer(String name){
        return lplRepository.howManyWickets(name);
    }

    @Override
    public String runsForPlayer(String name){
        return lplRepository.howManyRuns(name);
    }

    @Override
    public String howPlayerEarnsRun(int runType, String name){
        return lplRepository.howManyRunsCount(runType, name);
    }

    @Override
    public HashMap<String, String> playerRunsDetails(String name) {
        HashMap<String, String> details = new HashMap<>();

        details.put("totalRuns", lplRepository.howManyRuns(name));
        details.put("ballsFaced", String.valueOf(lplRepository.howManyBallsFaced(name)));
        details.put("dots", lplRepository.howManyRunsCount(0, name) == null ? "0" : lplRepository.howManyRunsCount(0, name));
        details.put("ones", lplRepository.howManyRunsCount(1, name) == null ? "0" : lplRepository.howManyRunsCount(1, name));
        details.put("twos", lplRepository.howManyRunsCount(2, name) == null ? "0" : lplRepository.howManyRunsCount(2, name));
        details.put("threes", lplRepository.howManyRunsCount(3, name) == null ? "0" : lplRepository.howManyRunsCount(3, name));
        details.put("fours", lplRepository.howManyRunsCount(4, name) == null ? "0" : lplRepository.howManyRunsCount(4, name));
        details.put("sixes", lplRepository.howManyRunsCount(6, name) == null ? "0" : lplRepository.howManyRunsCount(6, name));
        details.put("strikeRate", String.valueOf((Integer.parseInt(lplRepository.howManyRuns(name)) / (float) lplRepository.howManyBallsFaced(name)) * 100));
        return details;
    }
}
