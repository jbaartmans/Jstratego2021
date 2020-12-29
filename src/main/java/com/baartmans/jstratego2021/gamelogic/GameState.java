package com.baartmans.jstratego2021.gamelogic;

public class GameState {

    private Pawn[] team1;
    private Pawn[] team2;

    public Pawn[] getTeam1() {
        return team1;
    }

    public void setTeam1(Pawn[] team1) {
        this.team1 = team1;
    }

    public Pawn[] getTeam2() {
        return team2;
    }

    public void setTeam2(Pawn[] team2) {
        this.team2 = team2;
    }
}