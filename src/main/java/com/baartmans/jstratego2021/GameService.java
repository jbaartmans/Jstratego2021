package com.baartmans.jstratego2021;

import com.baartmans.jstratego2021.gamelogic.GameState;
import com.baartmans.jstratego2021.gamelogic.MoveResponse;
import com.baartmans.jstratego2021.gamelogic.Pawn;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class GameService{

    public GameService(){ }

    public GameState newGame(){

        GameState newGame = new GameState();
        //Create the teams
        newGame.setTeam1(createTeam(1));
        newGame.setTeam2(createTeam(2));
        return newGame;
    }

    private LinkedList<String> getStartingPiecesInGame(){

        LinkedList pieces = new LinkedList<String>();

        // TODO; hoeveel stukken zijn er van elk?
//        "Bomb"
//        "Marshal"
//        "General"
//        "Colonel"
//        "Major"
//        "Captain"
//        "Lieutenant"
//        "Sergeant"
//        "Miner"
//        "Scout:
//        "Spy"  1
//        "Flag"  1



//        Rang	Aantal per kleur
//        Bom	6
//        Maarschalk	1
//        Generaal	1
//        Kolonel	2
//        Majoor	3
//        Kapitein	4
//        Luitenant	4
//        Sergeant	4
//        Mineur	5
//        Verkenner	8
//        Spion	1
//        Vlag	1


        // 6 Bombs
        Collections.addAll(pieces, "Bomb","Bomb","Bomb","Bomb","Bomb","Bomb");
        // 1 Maarschalk
        pieces.add("Marshal");
        // 1 General
        pieces.add("General");
        // 2 Colonels
//        Collections.addAll(pieces, "Colonel","Colonel");
//        // 3 Majoors
//        Collections.addAll(pieces, "Major","Major");
//        // 4 Kapiteins
//        Collections.addAll(pieces, "Captain","Captain");
//
//        // 4 Luitenanten
//        Collections.addAll(pieces, "Lieutenant","Captain");


        pieces.add("Major");



        Collections.addAll(pieces, "Captain","Captain","Captain","Captain","Captain");

        Collections.addAll(pieces, "Sergeant","Sergeant","Sergeant","Sergeant","Sergeant");



        pieces.add("Flag");


        //Add all scoutsa
        for(int i = 0; i < 28; i++){
            pieces.add("Scout");
        }

        return  pieces;
    }

    private Pawn[] createTeam(int teamNumber){
        int yFrom = 0;
        int yTo = 3;

        if(teamNumber == 2){
            yFrom = 6;
            yTo = 9;
        }
        LinkedList pieces = this.getStartingPiecesInGame();
        Pawn[] team = new Pawn[40];
        int counter = 0;
        for(int y = yFrom; y <= yTo; y++){

            for(int x = 0; x < 10; x++){
                int[] team1Location = {x,y};
                int random = getRandom(pieces.size());
                team[counter] = new Pawn(pieces.get(random).toString(),team1Location);
                pieces.remove(random);
                counter++;
            }
        }
        return team;
    }


    // TODO move this function to util class
    // Read more: https://www.java67.com/2015/01/how-to-get-random-number-between-0-and-1-java.html#ixzz6iOvlQk81
    public static int getRandom(int max){ // return (int) (Math.random()*max); //incorrect always return zero
         return (int) (Math.random()*max);
    }

    // TODO
    public MoveResponse doMove(){
        MoveResponse mr = new MoveResponse();
        return mr;
    }
}


