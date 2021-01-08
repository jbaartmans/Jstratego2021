package com.baartmans.jstratego2021;

import com.baartmans.jstratego2021.gamelogic.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private GameService gs = new GameService();

    @CrossOrigin
    @GetMapping("/version")
    public String getVersion() {
        return  "JStratego2021 version 0.3";
    }

    @CrossOrigin
    @GetMapping("/newgame")
    public GameState newGame() {
        return  gs.newGame();
    }

    @CrossOrigin
    @RequestMapping(value = "/domove", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MoveResponse doMove(@RequestBody GameState gamestate) {

        //Omdat we uit de doMove alleen nog de GameState krijgen en niet de nog de Move maken we hier zelf een object aan om in ieder geval de logica in de GameService op te kunnen gaan zetten
        int[] from = {0,6};
        int[] to = {0,5};
        Move move = new Move(from,to);
        return  gs.doMove(gamestate, move,2);
    }

    @CrossOrigin
    @RequestMapping(value = "/domove/ai", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MoveResponse doMoveAI(@RequestBody GameState gamestate) {
        return  gs.doMoveAI(gamestate);
    }


    //TEST met input o.b.v GameInput Object
    @CrossOrigin
    @RequestMapping(value = "/domove/input", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MoveResponse doMovInput(@RequestBody GameInput gameinput) {
        return  gs.doMove(gameinput.getGameState(), gameinput.getMove(),2);
    }

    @CrossOrigin
    @RequestMapping(value = "/domove/input/ai", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MoveResponse doMoveAI(@RequestBody GameInput gameinput) {
        return  gs.doMoveAI(gameinput.getGameState());
    }

//    @GetMapping("/error")
//    public String handleError(HttpServletRequest request) {
//        return "error";
//    }

}