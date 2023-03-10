package com.example.rootcodelabslpl.api.v1.lpl.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lpl")
public class LPL {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private int inningsNumber;
    private double overAndBall;
    private String battingTeamName;
    private String batsman;
    private String nonStriker;
    private String bowler;
    private int runsOffBat;
    private int extras;
    private int wides;
    private int noBalls;
    private int byes;
    private int legByes;
    private String kindOfWicket;
    private String dismissedPlayed;
}
