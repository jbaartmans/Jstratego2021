package com.baartmans.jstratego2021;

import com.baartmans.jstratego2021.gamelogic.GameState;
import com.baartmans.jstratego2021.gamelogic.Move;
import com.baartmans.jstratego2021.gamelogic.MoveResponse;
import com.baartmans.jstratego2021.gamelogic.Pawn;
import com.baartmans.jstratego2021.gamelogic.enums.GameStatus;

import java.util.*;

public class GameService{

    //Gebruik een HashMap als KeyValue pair om Types om te zetten naar Rangen
    final static HashMap<String, Integer> typesAndRanks = new HashMap<>();

    static{
        /*Adding elements to HashMap*/
        typesAndRanks.put("Marshall", 10);
        typesAndRanks.put("General", 9);
        typesAndRanks.put("Colonel", 8);
        typesAndRanks.put("Major", 7);
        typesAndRanks.put("Captain", 6);
        typesAndRanks.put("Lieutenant", 5);
        typesAndRanks.put("Sergeant", 4);
        typesAndRanks.put("Miner", 3);
        typesAndRanks.put("Scout", 2);
        typesAndRanks.put("Spy", 1);
        typesAndRanks.put("Bomb", 0);
        typesAndRanks.put("Flag", 0);
    }

    public GameService(){ }

    public GameState newGame(){

        GameState newGame = new GameState();
        //Create the teams
        newGame.setTeam1(createTeam(1));
        newGame.setTeam2(createTeam(2));
        return newGame;
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

    private LinkedList<String> getStartingPiecesInGame(){

        LinkedList pieces = new LinkedList<String>();

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
        Collections.addAll(pieces, "Colonel","Colonel");
        // 3 Majoors
        Collections.addAll(pieces, "Major","Major","Major");
        // 4 Kapiteins
        Collections.addAll(pieces, "Captain","Captain","Captain","Captain");

        // 4 Luitenanten
        Collections.addAll(pieces, "Lieutenant","Lieutenant","Lieutenant","Lieutenant");

        // 4 Sergeanten
        Collections.addAll(pieces, "Sergeant","Sergeant","Sergeant","Sergeant");

        // 5 Mineurs
        Collections.addAll(pieces, "Miner","Miner","Miner","Miner","Miner");

        // 8 Verkenners
        Collections.addAll(pieces, "Scout","Scout","Scout","Scout","Scout","Scout","Scout","Scout");
        // 1 Spion
        Collections.addAll(pieces, "Spy");
        // 1 Vlag
        pieces.add("Flag");
        return  pieces;
    }

    // TODO move this function to util class
    // Read more: https://www.java67.com/2015/01/how-to-get-random-number-between-0-and-1-java.html#ixzz6iOvlQk81
    public static int getRandom(int max){ // return (int) (Math.random()*max); //incorrect always return zero
         return (int) (Math.random()*max);
    }

    // WIP
    public MoveResponse doMove(GameState gameState, Move move){

        //Could be other team in future
        Pawn[] currentTeam = gameState.getTeam2();
        Pawn[] enemyTeam = gameState.getTeam1();
        boolean pieceExists = false;
        Pawn currentPawn = null;

        //Controleer of het stuk bestaat

        System.out.println("Input Move");
        System.out.println(move.getFrom()[0]  + ":" +  move.getFrom()[1]);

        for(Pawn pawn : currentTeam) {
            System.out.println(pawn.getLocation()[0] + "::" + pawn.getLocation()[1]);
            if (pawn.getLocation()[0] == move.getFrom()[0]
                    && pawn.getLocation()[1] == move.getFrom()[1]){
                System.out.println("Stuk gevonden, het is een" + pawn.getType());
                pieceExists = true;
                currentPawn = pawn;
                break;

            }
        }

        if(!pieceExists){
            return new MoveResponse(
                    false,
                    "This piece is not yours",
                    GameStatus.PLAYING.toString()
            );
        }

        if(currentPawn.getType().equalsIgnoreCase("Bomb") || currentPawn.getType().equalsIgnoreCase("Flag")){
            return new MoveResponse(
                    false,
                    "This piece can't be moved",
                    GameStatus.PLAYING.toString()
            );
        }

        //Left Lake
        if( (move.getTo()[0] == 4 && move.getTo()[1] == 2)
                || move.getTo()[0] == 4 && move.getTo()[1] == 3
                || move.getTo()[0] == 5 && move.getTo()[1] == 2
                || move.getTo()[0] == 5 && move.getTo()[1] == 3 ){

            return new MoveResponse(
                    false,
                    "You can't move to a lake",
                    GameStatus.PLAYING.toString()
            );
        }

        //Right lake
        if( (move.getTo()[0] == 4 && move.getTo()[1] == 6)
                || move.getTo()[0] == 4 && move.getTo()[1] == 7
                || move.getTo()[0] == 5 && move.getTo()[1] == 6
                || move.getTo()[0] == 5 && move.getTo()[1] == 7 ){

            return new MoveResponse(
                    false,
                    "You can't move to a lake",
                    GameStatus.PLAYING.toString()
            );
        }

        //To locatie is niet bezet met eigen stuk?
        for(Pawn pawn : currentTeam) {
            if (pawn.getLocation()[0] == move.getTo()[0]
                    && pawn.getLocation()[1] == move.getTo()[1]){
                return new MoveResponse(
                        false,
                        "Piece cannot move to a position that is already occupied by another of your pieces",
                        GameStatus.PLAYING.toString()
                );
            }
        }

        for(Pawn pawn : enemyTeam) {
            if (pawn.getLocation()[0] == move.getTo()[0]
                    && pawn.getLocation()[1] == move.getTo()[1]){

                //Move is to an enemy piece!
                System.out.println("Move is naar de tegenstander");



                return doAttack(currentPawn, pawn);
            }
        }

        return new MoveResponse(
                true,
                "Piece moved to .....",
                GameStatus.PLAYING.toString()
        );

        // Checks stappen plan:

        // Bestaat dit stuk? --> Done
        // Is het een eigen stuk? --> Done

        // Mag het stuk wel bewegen?  Vlag en Bomb mogen niet bewegen namelijk --> Done

        // Is de move  zelf valide? //TODO
        // Is deze niet te groot of schuin? //TODO

        // Is de to locatie niet in een van de twee meren? --> Done

        // Staat er een ander eigen stuk? Is het To locatie niet al bezet? --> Done

        // Staat er een stuk van de tegenstander?
            // Is het vlag? --> WIN --> Done
            // Is het een bom --?
            // Ben jelf een mineur een is de to en bomb --> maak bomb onschadelijk
            // Ben je zelf een Spion en val je de Maarschalk aan?
            // Wie heeft de hoogste rang?
    }

    private MoveResponse doAttack(Pawn attackingPawn, Pawn defendingPawn){

        String attackingPawnType =  attackingPawn.getType();
        String defendingPawnType =  defendingPawn.getType();

        // Is de vlag gevangen?
        if(defendingPawnType.equalsIgnoreCase("Flag")){
            return new MoveResponse(
                    true,
                    "The flag has been caputured! You have won the game!",
                    GameStatus.WON.toString()
            );
        }

        //TODO: scenario's met BOMB en SPION
        System.out.println("attackingPawnType: " + attackingPawnType);
        System.out.println("defendingPawnType: " + defendingPawnType);

        int attackingPawnRank = GameService.typesAndRanks.get(attackingPawnType);
        int defendingPawnRank = GameService.typesAndRanks.get(defendingPawnType);

        //Bij gelijke rangen wint het het aanvallende stuk
        if(attackingPawnRank >= defendingPawnRank ){
            //Aanvaller wint!
            return new MoveResponse(
                    true,
                    "You have won the attack with a " + attackingPawnType + " from a " + defendingPawnType,
                    GameStatus.PLAYING.toString()
            );
        } else {
            //Verdediger wint!
            return new MoveResponse(
                    true,
                    "You have lost the attack with a " + attackingPawnType + " from a " + defendingPawnType,
                    GameStatus.PLAYING.toString()
            );
        }
    }
}


