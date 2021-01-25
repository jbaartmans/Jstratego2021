package com.baartmans.jstratego2021;

import com.baartmans.jstratego2021.gamelogic.GameState;
import com.baartmans.jstratego2021.gamelogic.Move;
import com.baartmans.jstratego2021.gamelogic.MoveResponse;
import com.baartmans.jstratego2021.gamelogic.Pawn;
import com.baartmans.jstratego2021.gamelogic.enums.GameStatus;
import com.baartmans.jstratego2021.util.Random;
import java.util.*;

public class GameService {

    //Gebruik een HashMap als KeyValue pair om Types om te zetten naar Rangen
    final static HashMap<String, Integer> typesAndRanks = new HashMap<>();

    static {
        /*Adding elements to HashMap*/
        typesAndRanks.put("Marshal", 10);
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

    public GameService() {
    }

    public GameState newGame() {

        GameState newGame = new GameState();
        //Create the teams
        newGame.setTeam1(createTeam(1));
        newGame.setTeam2(createTeam(2));
        return newGame;
    }

    private Pawn[] createTeam(int teamNumber) {
        int yFrom = 0;
        int yTo = 3;

        if (teamNumber == 2) {
            yFrom = 6;
            yTo = 9;
        }
        LinkedList pieces = this.getStartingPiecesInGame();
        Pawn[] team = new Pawn[40];
        int counter = 0;
        for (int y = yFrom; y <= yTo; y++) {

            for (int x = 0; x < 10; x++) {
                int[] team1Location = {x, y};
                int random = Random.getRandom(pieces.size());
                team[counter] = new Pawn(pieces.get(random).toString(), team1Location);
                pieces.remove(random);
                counter++;
            }
        }
        return team;
    }

    private LinkedList<String> getStartingPiecesInGame() {
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
        Collections.addAll(pieces, "Bomb", "Bomb", "Bomb", "Bomb", "Bomb", "Bomb");
        // 1 Maarschalk
        pieces.add("Marshal");
        // 1 General
        pieces.add("General");
        // 2 Colonels
        Collections.addAll(pieces, "Colonel", "Colonel");
        // 3 Majoors
        Collections.addAll(pieces, "Major", "Major", "Major");
        // 4 Kapiteins
        Collections.addAll(pieces, "Captain", "Captain", "Captain", "Captain");
        // 4 Luitenanten
        Collections.addAll(pieces, "Lieutenant", "Lieutenant", "Lieutenant", "Lieutenant");
        // 4 Sergeanten
        Collections.addAll(pieces, "Sergeant", "Sergeant", "Sergeant", "Sergeant");
        // 5 Mineurs
        Collections.addAll(pieces, "Miner", "Miner", "Miner", "Miner", "Miner");
        // 8 Verkenners
        Collections.addAll(pieces, "Scout", "Scout", "Scout", "Scout", "Scout", "Scout", "Scout", "Scout");
        // 1 Spion
        Collections.addAll(pieces, "Spy");
        // 1 Vlag
        pieces.add("Flag");
        return pieces;
    }

    public MoveResponse doMove(GameState gameState, Move move, int team) {
        Pawn[] currentTeam = gameState.getTeam2();
        Pawn[] enemyTeam = gameState.getTeam1();

        // Dit is een move voor de AI, dus zijn de teams omgewisseld
        if(team == 1){
            currentTeam = gameState.getTeam1();
            enemyTeam = gameState.getTeam2();
        }

        Pawn currentPawn = null;

        //Controleer of het stuk bestaat
        for (Pawn pawn : currentTeam) {
            if (pawn.getLocation()[0] == move.getFrom()[0]
                    && pawn.getLocation()[1] == move.getFrom()[1]) {
                currentPawn = pawn;
                break;
            }
        }

        if (currentPawn == null) {
            return new MoveResponse(
                    false,
                    "This piece is not yours",
                    GameStatus.PLAYING.toString()
            );
        }

        if (currentPawn.getType().equalsIgnoreCase("Bomb") || currentPawn.getType().equalsIgnoreCase("Flag")) {
            return new MoveResponse(
                    false,
                    "This piece can't be moved",
                    GameStatus.PLAYING.toString()
            );
        }

        if(isLakeLocation(move.getTo()[0],move.getTo()[1])){
            return new MoveResponse(
                    false,
                    "You can't move to a lake",
                    GameStatus.PLAYING.toString()
            );
        }

        //To locatie is niet bezet met eigen stuk?
        for (Pawn pawn : currentTeam) {
            if (pawn.getLocation()[0] == move.getTo()[0]
                    && pawn.getLocation()[1] == move.getTo()[1]) {
                return new MoveResponse(
                        false,
                        "Piece cannot move to a position that is already occupied by another of your pieces",
                        GameStatus.PLAYING.toString()
                );
            }
        }

        // Controleer of het stuk niet te ver wil lopen
        boolean moveToIsInSurroundingPosition = false;
        List<Pawn> surroundingPositions;

        //Haal alle mogelijke locatie's op waar het stuk naartoe mag bewegen
        surroundingPositions = getSurroundingPositions(currentPawn, currentTeam, enemyTeam);
        Iterator<Pawn> iterator = surroundingPositions.iterator();
        while (iterator.hasNext()){
            Pawn availablePawn = iterator.next();
            int[] currLocation = availablePawn.getLocation();

            if(currLocation[0] == move.getTo()[0] && currLocation[1] == move.getTo()[1]){
                moveToIsInSurroundingPosition = true;
            }
        }

         if(!moveToIsInSurroundingPosition){
             return new MoveResponse(
                     false,
                     "Piece cannot move that far",
                     GameStatus.PLAYING.toString()
             );
         }

        for (Pawn pawn : enemyTeam) {
            if (pawn.getLocation()[0] == move.getTo()[0]
                    && pawn.getLocation()[1] == move.getTo()[1]) {
                //Move is to an enemy piece!
                return doAttack(currentPawn, pawn, team, move);
            }
        }

        // Anders is het een gewone Move zonder aanval
        return new MoveResponse(
                true,
                "Piece moved to [" + move.getTo()[0] + "," + move.getTo()[1] + "]",
                GameStatus.PLAYING.toString(),
                move
        );
    }

    private MoveResponse doAttack(Pawn attackingPawn, Pawn defendingPawn, int team, Move move) {
        String attackingPawnType = attackingPawn.getType();
        String defendingPawnType = defendingPawn.getType();
        //Is this move by the AI (team == 1) or the human player(team == 2)
        boolean humanTeam = team == 2;

        // Is de vlag gevangen?
        if (defendingPawnType.equalsIgnoreCase("Flag")) {
            String message1 = humanTeam ? "You have caputured the Flag! You have won the game!" : "Your flag has been caputured by the enemy! You have lost the game!";
            GameStatus gamestatus1 = humanTeam ? GameStatus.WON : GameStatus.LOST;

            return new MoveResponse(
                    true,
                     message1,
                    gamestatus1.toString()
            );
        }

        //Is het verdedigende stuk een Bomb?
        if (defendingPawnType.equalsIgnoreCase("Bomb")) {

            // Een Miner kan de Bomb onschadelijk maken
            if (attackingPawnType.equalsIgnoreCase("Miner")) {
                String message2 = humanTeam ? "An enemy Bomb has been removed by Miner" : "Your Bomb has been removed by an enemy Miner";
                return new MoveResponse(
                        true,
                        message2,
                        GameStatus.PLAYING.toString(),
                        move,
                        defendingPawn.getLocation()
                );
            } else {
                String message3 = humanTeam ? "Kaboom! Your" + attackingPawnType + " has been blown up by a enemy Bomb!" : "Kaboom! An enemy " + attackingPawnType + " has been blown up by your Bomb!";
                return new MoveResponse(
                        true,
                        message3,
                        GameStatus.PLAYING.toString(),
                        move,
                        attackingPawn.getLocation()
                );
            }
        }

        // Een aanvallende Spy kan een verdedigende Marshal uitschakelen
        if (attackingPawnType.equalsIgnoreCase("Spy") && defendingPawnType.equalsIgnoreCase("Marshal")) {
            String message4 = humanTeam ? "The enemy Marshal has been killed by your Spy" : "Your Marshal has been killed by an enemy Spy";
            return new MoveResponse(
                    true,
                     message4,
                    GameStatus.PLAYING.toString(),
                    move,
                    defendingPawn.getLocation()
            );
        }

        //Bepaal de rangen van de stukken
        int attackingPawnRank = GameService.typesAndRanks.get(attackingPawnType);
        int defendingPawnRank = GameService.typesAndRanks.get(defendingPawnType);

        //Bij gelijke rangen wint het het aanvallende stuk
        if (attackingPawnRank >= defendingPawnRank) {
            String message5 = humanTeam ? "You have won the attack with a " + attackingPawnType + " from a " + defendingPawnType : "The enemy has won the attack with a " + attackingPawnType + " from a " + defendingPawnType;
            //Aanvaller wint!
            return new MoveResponse(
                    true,
                    message5,
                    GameStatus.PLAYING.toString(),
                    move,
                    defendingPawn.getLocation()
            );
        } else {
            //Verdediger wint!
            String message6 = humanTeam ? "You have lost the attack with a " + attackingPawnType + " from a " + defendingPawnType : "The enemy has lost the attack with a " + attackingPawnType + " from a " + defendingPawnType;
            return new MoveResponse(
                    true,
                    message6,
                    GameStatus.PLAYING.toString(),
                    move,
                    attackingPawn.getLocation()
            );
        }
    }

    public MoveResponse doMoveAI(GameState gameState) {
        Pawn[] currentTeam = gameState.getTeam1();
        Pawn[] enemyTeam = gameState.getTeam2();
        Pawn currentPawn = null;
        List<Pawn> surroundingPositions = null;

        // Get a random pawn from the AI team (== team1)
        while (currentPawn == null && surroundingPositions == null) {
            int random = Random.getRandom(currentTeam.length - 1);
            currentPawn = currentTeam[random];
            String currentPawnType = currentPawn.getType();
            if (currentPawnType.equalsIgnoreCase("Bomb") || currentPawnType.equalsIgnoreCase("Flag")) {
                // Do not select the Bomb or Pawn as piece to move
                currentPawn = null;
            }
            else {
                surroundingPositions = getSurroundingPositions( currentPawn, currentTeam, enemyTeam);
                if(surroundingPositions.size() == 0){
                    surroundingPositions = null;
                    currentPawn = null;
                }
            }
        }

        MoveResponse validMoveResponse = null;

        // Proberen om een valide move te maken
        while(validMoveResponse == null){
            //Haal een random locatie op waar niet een stuk van het eigen team staat
            int random = Random.getRandom(surroundingPositions.size());
            int[] proposedLocation = surroundingPositions.get(random).getLocation();

            //Nieuwe move samenstellen
            Move proposedMove = new Move(currentPawn.getLocation(), proposedLocation);

            //Do de move
            MoveResponse proposedMoveResponse = doMove(gameState, proposedMove,1);

            //Is het een valide move, dan kunnen we uit de loop breaken
            if(proposedMoveResponse.isValidMove()){
                validMoveResponse = proposedMoveResponse;
                break;
            } else {
                // For debug purposes
                System.out.println(proposedMoveResponse.getResponseMessage());
            }
        }

        return validMoveResponse;
    }

    private List<Pawn> getAvailableLocations(Pawn[] team) {
        List<Pawn> allAvailableLocations = new ArrayList<>();

        //Maak een lijst van het volledige speelveld van 10 x 10
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                int[] teamLocation = {x, y};
                allAvailableLocations.add(new Pawn("EMPTY", teamLocation));
            }
        }

        // Verwijder de plekken uit het volledige speelveld die al bezet zijn door het Team(1)
        Iterator<Pawn> iterator = allAvailableLocations.iterator();
        while (iterator.hasNext()){
            Pawn availablePawn = iterator.next();
            int[] currLocation = availablePawn.getLocation();
            for (Pawn pawn : team) {
                int[] currentPawnLocation = pawn.getLocation();
                if (currentPawnLocation[0] == currLocation[0] && currentPawnLocation[1] == currLocation[1]) {
                    iterator.remove();
                }
            }
        }
        return allAvailableLocations;
    }

    private List<Pawn> getSurroundingPositions(Pawn currentPawn, Pawn[] currentTeam,  Pawn[] enemyTeam){
        List<Pawn> surroundingLocations = new ArrayList<>();
        int xFrom = currentPawn.getLocation()[0];
        int yFrom = currentPawn.getLocation()[1];
        boolean isScout = currentPawn.getType().equalsIgnoreCase("Scout");

        //4 scenarios; voor achter, links rechts
        int xFromPositive = xFrom +1;
        //Voor
        while(true) {
            Pawn xFromPositivePawn = checkSurroundingPosition(xFromPositive, yFrom, currentTeam, enemyTeam, isScout);
            if(xFromPositivePawn != null){
                surroundingLocations.add(xFromPositivePawn);
                if(xFromPositivePawn.getType().equalsIgnoreCase("ENEMY")) {
                    //Dit stuk is van de vijand, voeg dit stuk nog wel toe, maar breek uit de loop daarna
                    break;
                }
            } else {
                break;
            }
            if(!isScout){
                //Als het geen Scout is, dan mag er maar 1 vakje gelopen worden
                break;
            }
            xFromPositive++;
        }

        //Achter
        int xFromNegative = xFrom - 1;
        while(true) {
            Pawn xFromNegativePawn = checkSurroundingPosition(xFromNegative, yFrom, currentTeam, enemyTeam, isScout );
            if(xFromNegativePawn != null){
                surroundingLocations.add(xFromNegativePawn);
                if(xFromNegativePawn.getType().equalsIgnoreCase("ENEMY")) {
                    //Dit stuk is van de vijand, voeg dit stuk nog wel toe, maar breek uit de loop daarna
                    break;
                }
            } else {
                break;
            }
            if(!isScout){
                //Als het geen Scout is, dan mag er maar 1 vakje gelopen worden
                break;
            }
            xFromNegative--;
        }

        //Links
        int yFromPositive = yFrom +1;
        while(true) {
            Pawn yFromPositivePawn = checkSurroundingPosition(xFrom, yFromPositive, currentTeam, enemyTeam, isScout);
            if(yFromPositivePawn != null){
                surroundingLocations.add(yFromPositivePawn);
                if(yFromPositivePawn.getType().equalsIgnoreCase("ENEMY")) {
                    //Dit stuk is van de vijand, voeg dit stuk nog wel toe, maar breek uit de loop daarna
                    break;
                }
            } else {
                break;
            }
            if(!isScout){
                //Als het geen Scout is, dan mag er maar 1 vakje gelopen worden
                break;
            }
            yFromPositive++;
        }

        //Rechts
        int yFromNegative = yFrom - 1;
        while(true) {
            Pawn yFromNegativePawn = checkSurroundingPosition(xFrom, yFromNegative, currentTeam,enemyTeam, isScout );
            if(yFromNegativePawn != null){
                surroundingLocations.add(yFromNegativePawn);
                if(yFromNegativePawn.getType().equalsIgnoreCase("ENEMY")) {
                    //Dit stuk is van de vijand, voeg dit stuk nog wel toe, maar breek uit de loop daarna
                    break;
                }
            } else {
                break;
            }
            if(!isScout){
                //Als het geen Scout is, dan mag er maar 1 vakje gelopen worden
                break;
            }
            yFromNegative--;
        }

        return surroundingLocations;
    }

    // Controleer of de omliggende locatie vrij en voeg deze toe aan lijst en return deze
    private Pawn checkSurroundingPosition(int x, int y, Pawn[] currentTeam, Pawn[] enemyTeam, boolean checkEnemyTeam ){

        //Valt het stuk wel binnen het speel veld?
        if( x >=0 && y >= 0 &&  x <= 9 && y <= 9){
            boolean locationOccupied = false;

            if(checkEnemyTeam){
                boolean isEnemyLocation = false;

                for (Pawn pawn : enemyTeam) {
                    int[] currentPawnLocation = pawn.getLocation();
                    if (currentPawnLocation[0] == x  && currentPawnLocation[1] == y) {
                        isEnemyLocation = true;
                        break;
                    }
                }

                if(isEnemyLocation){
                    //Als het de locatie van de vijand is, dan geven we wel deze locatie terug
                    int[] surroundingLocation = {x, y};
                    return new Pawn("ENEMY", surroundingLocation);
                }
            }

            for (Pawn pawn : currentTeam) {
                int[] currentPawnLocation = pawn.getLocation();
                if (currentPawnLocation[0] == x  && currentPawnLocation[1] == y) {
                    locationOccupied = true;
                    break;
                }
            }

            if(!locationOccupied && !isLakeLocation(x,y)){
                int[] surroundingLocation = {x, y};
                return new Pawn("EMPTY", surroundingLocation);
            }
        }

        return  null;
    }

    private boolean isLakeLocation(int x, int y){
        //Left Lake
        if ((y == 4 && x == 2)
                || (y == 4 && x == 3)
                || (y == 5 && x == 2)
                || (y == 5 && x == 3)) {
             return true;
        }

        // Right lake
        if ((y == 4 && x == 6)
                || (y == 4 && x == 7)
                || (y == 5 && x == 6)
                || (y == 5 && x == 7)) {
            return true;
        }

        return false;
    }
}