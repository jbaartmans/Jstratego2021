package com.baartmans.jstratego2021.gamelogic;

public class Pawn {

    private  String type;
    private int[] location;

    public Pawn(String type, int[] location){
        this.type = type;
        this.location = location;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }
}
