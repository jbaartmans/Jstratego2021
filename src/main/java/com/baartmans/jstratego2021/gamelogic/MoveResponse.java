package com.baartmans.jstratego2021.gamelogic;

public class MoveResponse {

    private boolean isValidMove;
    private String responseMessage;
    private String gameStatus;

    public MoveResponse(){
        isValidMove = false;
        responseMessage = "TEST";
        gameStatus = "IN_PROGRESS";
    }

    public boolean isValidMove() {
        return isValidMove;
    }

    public void setValidMove(boolean validMove) {
        isValidMove = validMove;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}