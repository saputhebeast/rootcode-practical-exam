package com.example.rootcodelabslpl.api.v1.lpl.repository;

import com.example.rootcodelabslpl.api.v1.lpl.model.LPL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LPLRepository extends JpaRepository<LPL, Integer> {
    @Query(value = "SELECT SUM(runs_off_bat + extras + wides + no_balls + byes + leg_byes) AS 'total' FROM lpl.lpl WHERE batting_team_name = ?1", nativeQuery = true)
    int getTotalTeamScore(String teamName);

    @Query(value = "SELECT MAX(over_and_ball) AS 'overs' FROM lpl.lpl WHERE innings_number = ?1", nativeQuery = true)
    double getBalledOversFromAnInning(int inning);

    @Query(value = "SELECT batsman AS 'runs' FROM lpl.lpl GROUP BY batsman ORDER BY SUM(runs_off_bat) DESC limit 1", nativeQuery = true)
    String topRunScorerName();

    @Query(value = "SELECT SUM(runs_off_bat) AS 'runs' FROM lpl.lpl GROUP BY batsman ORDER BY SUM(runs_off_bat) DESC limit 1", nativeQuery = true)
    String topRunScorerScore();

    @Query(value = "SELECT bowler from lpl.lpl WHERE kind_of_wicket IN (\"bowled\", \"caught\") GROUP BY bowler ORDER BY COUNT(kind_of_wicket) DESC limit 1", nativeQuery = true)
    String highestWicketTakerName();

    @Query(value = "SELECT COUNT(kind_of_wicket) from lpl.lpl WHERE kind_of_wicket IN (\"bowled\", \"caught\") GROUP BY bowler ORDER BY COUNT(kind_of_wicket) DESC limit 1", nativeQuery = true)
    String highestWicketTakerTakenWickets();

    @Query(value = "select batsman from lpl.lpl where batsman like %?1% ORDER BY batsman LIMIT 1", nativeQuery = true)
    String isPlayerBatsman(String name);

    @Query(value = "select bowler from lpl.lpl where bowler like %?1% ORDER BY bowler LIMIT 1", nativeQuery = true)
    String isPlayerBowler(String name);

    @Query(value = "SELECT SUM(runs_off_bat) AS 'runs' FROM lpl.lpl WHERE batsman like ?1", nativeQuery = true)
    String howManyRuns(String name);

    @Query(value = "SELECT COUNT(kind_of_wicket) from lpl.lpl WHERE kind_of_wicket IN (\"bowled\", \"caught\") and bowler like ?1", nativeQuery = true)
    int howManyWickets(String name);

    @Query(value = "SELECT count(*) FROM lpl.lpl WHERE runs_off_bat = ?1 group by batsman having batsman like %?2%", nativeQuery = true)
    String howManyRunsCount(int runType, String batsman);

    @Query(value = "SELECT COUNT(*) FROM lpl.lpl GROUP BY batsman having batsman like ?1", nativeQuery = true)
    int howManyBallsFaced(String batsman);
}
