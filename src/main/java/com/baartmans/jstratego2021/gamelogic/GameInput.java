package com.baartmans.jstratego2021.gamelogic;

public class GameInput {

    private GameState gameState;
    private Move move;

    public GameInput(GameState gameState, Move move){
        this.gameState = gameState;
        this.move = move;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}
