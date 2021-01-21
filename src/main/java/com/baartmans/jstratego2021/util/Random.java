package com.baartmans.jstratego2021.util;

public class Random{
    // Read more: https://www.java67.com/2015/01/how-to-get-random-number-between-0-and-1-java.html#ixzz6iOvlQk81
    public static int getRandom(int max) { // return (int) (Math.random()*max); //incorrect always return zero
        return (int) (Math.random() * max);
    }
}
