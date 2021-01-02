package com.baartmans.jstratego2021;

import com.baartmans.jstratego2021.gamelogic.GameState;
import com.baartmans.jstratego2021.gamelogic.MoveResponse;
import com.baartmans.jstratego2021.gamelogic.Pawn;

/* This service is used to provide different scenario's for the Svelte Frontend to TEST/ Develop the game */

public class GameTestService {

        public GameTestService(){ }

        public GameState newTestGame(){
            GameState newGame = new GameState();
            //Build the teams
            // Team1
            int[] team1Location1 = {0,0};
            int[] team1Location2 = {0,1};
            int[] team1Location3 = {0,2};
            int[] team1Location4 = {0,4};

            Pawn[] team1 = new Pawn[4];
            team1[0] = new Pawn("Flag",team1Location1);
            team1[1] = new Pawn("Scout",team1Location2);
            team1[2] = new Pawn("Scout",team1Location3);
            team1[3] = new Pawn("Scout",team1Location4);
            newGame.setTeam1(team1);

            // Team2
            int[] team2Location1 = {0,9};
            int[] team2Location2 = {1,9};
            int[] team2Location3 = {2,9};
            int[] team2Location4 = {3,9};

            Pawn[] team2 = new Pawn[4];
            team2[0] = new Pawn("Flag",team2Location1);
            team2[1] = new Pawn("Scout",team2Location2);
            team2[2] = new Pawn("Scout",team2Location3);
            team2[3] = new Pawn("Scout",team2Location4);
            newGame.setTeam2(team2);
            return newGame;
        }
}
