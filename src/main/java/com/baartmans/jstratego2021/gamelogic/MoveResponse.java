package com.baartmans.jstratego2021.gamelogic;

public class MoveResponse {

    private boolean isValidMove;
    private String responseMessage;
    private String gameStatus;
    private int[] removePiece;
    private  Move move;

    public MoveResponse(boolean isValidMove, String responseMessage, String gameStatus){
        this.isValidMove = isValidMove;
        this.responseMessage = responseMessage;
        this.gameStatus = gameStatus;
    }

    public MoveResponse(boolean isValidMove, String responseMessage, String gameStatus, Move move){
        this.isValidMove = isValidMove;
        this.responseMessage = responseMessage;
        this.gameStatus = gameStatus;
        this.move = move;
    }

    public MoveResponse(boolean isValidMove, String responseMessage, String gameStatus, Move move, int[] removePiece){
        this.isValidMove = isValidMove;
        this.responseMessage = responseMessage;
        this.gameStatus = gameStatus;
        this.move = move;
        this.removePiece = removePiece;
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

    public int[] getRemovePiece() { return removePiece;   }

    public void setRemovePiece(int[] removePiece) { this.removePiece = removePiece; }

    public Move getMove() {  return move;  }

    public void setMove(Move move) {  this.move = move;  }
}