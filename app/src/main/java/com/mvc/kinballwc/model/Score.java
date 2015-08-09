package com.mvc.kinballwc.model;

import com.mvc.kinballwc.application.App;
import com.mvc.kinballwc.utils.Utils;

import java.util.Comparator;

/**
 * Author: Mario Velasco Casquero
 * Date: 09/08/2015
 * Email: m3ario@gmail.com
 */
public class Score {

    private String name;
    private int match1Score;
    private int match2Score;
    private int match3Score;
    private int totalScore;

    private int firstPositionCount;
    private int secondPositionCount;
    private int wonPeriodsCount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMatch1Score() {
        return match1Score;
    }

    public void setMatch1Score(int match1Score) {
        this.match1Score = match1Score;
    }

    public int getMatch2Score() {
        return match2Score;
    }

    public void setMatch2Score(int match2Score) {
        this.match2Score = match2Score;
    }

    public int getMatch3Score() {
        return match3Score;
    }

    public void setMatch3Score(int match3Score) {
        this.match3Score = match3Score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getFirstPositionCount() {
        return firstPositionCount;
    }

    public void setFirstPositionCount(int firstPositionCount) {
        this.firstPositionCount = firstPositionCount;
    }

    public int getSecondPositionCount() {
        return secondPositionCount;
    }

    public void setSecondPositionCount(int secondPositionCount) {
        this.secondPositionCount = secondPositionCount;
    }

    public int getWonPeriodsCount() {
        return wonPeriodsCount;
    }

    public void setWonPeriodsCount(int wonPeriodsCount) {
        this.wonPeriodsCount = wonPeriodsCount;
    }

    public static class ScoreComparator implements Comparator<Score> {
        @Override
        public int compare(Score score1, Score score2) {
            int result = score2.getTotalScore() - score1.getTotalScore();
            if (result == 0) {
                result = score2.getFirstPositionCount() - score1.getFirstPositionCount();
                if (result == 0) {
                    result = score2.getSecondPositionCount() - score1.getSecondPositionCount();
                    if (result == 0) {
                        result = score2.getWonPeriodsCount() - score1.getWonPeriodsCount();
                    }
                }
            }
            return result;
        }
    }
}
