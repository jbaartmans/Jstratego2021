package com.baartmans.jstratego2021;

import java.util.concurrent.atomic.AtomicLong;

import com.baartmans.jstratego2021.gamelogic.GameState;
import com.baartmans.jstratego2021.gamelogic.Greeting;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GameController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }


    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello JStratego! %s!", name);
    }

    @CrossOrigin
    @GetMapping("/newgame")
    public GameState newGame() {
        GameService gs = new GameService();
        return  gs.newGame();
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        GameService gs = new GameService();
        return "error";
    }
}