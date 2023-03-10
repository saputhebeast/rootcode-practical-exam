package com.example.rootcodelabslpl.api.v1.lpl.controller;

import com.example.rootcodelabslpl.api.v1.lpl.dto.ErrorResponseDTO;
import com.example.rootcodelabslpl.api.v1.lpl.dto.GameDetailsSaveRequestDTO;
import com.example.rootcodelabslpl.api.v1.lpl.dto.MatchSummaryResponseDTO;
import com.example.rootcodelabslpl.api.v1.lpl.dto.PlayerSummaryDTO;
import com.example.rootcodelabslpl.api.v1.lpl.model.LPL;
import com.example.rootcodelabslpl.api.v1.lpl.service.impl.LPLServiceImpl;
import com.example.rootcodelabslpl.utils.StatusList;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/lpl")
public class LPLController {
    @Autowired
    private LPLServiceImpl lplService;
    @Autowired
    private MatchSummaryResponseDTO matchSummaryResponseDTO;
    @Autowired
    private PlayerSummaryDTO playerSummaryDTO;
    @Autowired
    private ErrorResponseDTO errorResponseDTO;

    @PostMapping
    public ResponseEntity createMatchData(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return new ResponseEntity("empty file", HttpStatusCode.valueOf(StatusList.HTTP_ERROR));
        }else{
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                // create csv bean reader
                CsvToBean<GameDetailsSaveRequestDTO> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(GameDetailsSaveRequestDTO.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                // convert `CsvToBean` object to list of users
                List<GameDetailsSaveRequestDTO> matches = csvToBean.parse();

                List<LPL> addGames = new ArrayList<>();

                for(int i = 0; i < matches.size(); i++){
                    LPL row = new LPL();
                    row.setInningsNumber(matches.get(i).getInningsNumber());
                    row.setOverAndBall(matches.get(i).getOverAndBall());
                    row.setBattingTeamName(matches.get(i).getBattingTeamName());
                    row.setBatsman(matches.get(i).getBatsman());
                    row.setNonStriker(matches.get(i).getNonStriker());
                    row.setBowler(matches.get(i).getBowler());
                    row.setRunsOffBat(matches.get(i).getRunsOffBat());
                    row.setExtras(matches.get(i).getExtras());
                    row.setWides(matches.get(i).getWides());
                    row.setNoBalls(matches.get(i).getNoBalls());
                    row.setByes(matches.get(i).getByes());
                    row.setLegByes(matches.get(i).getLegByes());
                    row.setKindOfWicket(matches.get(i).getKindOfWicket());
                    row.setDismissedPlayed(matches.get(i).getDismissedPlayed());
                    addGames.add(row);
                }
                return new ResponseEntity(lplService.saveGameDetails(addGames), HttpStatusCode.valueOf(StatusList.HTTP_SUCCESS));
            } catch (Exception ex) {
                // logger
                return new ResponseEntity("something went wrong!", HttpStatusCode.valueOf(StatusList.HTTP_SUCCESS));
            }
        }
    }

    @GetMapping
    public ResponseEntity getSummary(){
        matchSummaryResponseDTO.setWinningTeamName(lplService.winningTeam());
        matchSummaryResponseDTO.setScoresOfEachTeam(lplService.scoresOfTeams());
        matchSummaryResponseDTO.setPlayedOverInEachInnings(lplService.playedOverInEachInnings());
        matchSummaryResponseDTO.setMostRunsScorer(lplService.mostRunScorer());
        matchSummaryResponseDTO.setMostWicketsTaker(lplService.highestWicketTaker());

        return new ResponseEntity(matchSummaryResponseDTO, HttpStatusCode.valueOf(StatusList.HTTP_ERROR));
    }

    @GetMapping("{name}")
    public ResponseEntity getPlayerSummary(@PathVariable("name") String name){
        if(lplService.isPlayerExist(name)){
            playerSummaryDTO.setName(lplService.matchingPlayerName(name));
            playerSummaryDTO.setType(lplService.playerType(lplService.matchingPlayerName(name)));
            playerSummaryDTO.setRuns(lplService.runsForPlayer(lplService.matchingPlayerName(name)));
            playerSummaryDTO.setWickets(lplService.wicketsForPlayer(lplService.matchingPlayerName(name)));
            return new ResponseEntity(playerSummaryDTO, HttpStatusCode.valueOf(StatusList.HTTP_SUCCESS));
        }else{
            errorResponseDTO.setMessage("Please check the player name again!");
            return new ResponseEntity(errorResponseDTO, HttpStatusCode.valueOf(StatusList.HTTP_ERROR));
        }
    }
}
