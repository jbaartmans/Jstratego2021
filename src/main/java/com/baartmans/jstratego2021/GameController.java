package com.baartmans.jstratego2021;

import java.util.concurrent.atomic.AtomicLong;

import com.baartmans.jstratego2021.gamelogic.GameState;
import com.baartmans.jstratego2021.gamelogic.Greeting;
import com.baartmans.jstratego2021.gamelogic.MoveResponse;
import com.baartmans.jstratego2021.gamelogic.Pawn;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;

@RestController
public class GameController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private GameService gs = new GameService();

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
        return  gs.newGame();
    }

    @CrossOrigin
    @RequestMapping(value = "/domove", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MoveResponse doMove(@RequestBody GameState gamestate) {
        GameService a = new GameService(); //?? waarom weer een nieuwe instantie?

        System.out.println("DoMove wordt aangeroepen");
        for(Pawn i : gamestate.getTeam1()){
            System.out.println(i.getType());
        }
        return  a.doMove();
    }

//    @GetMapping("/error")
//    public String handleError(HttpServletRequest request) {
//        return "error";
//    }

}