package com.baartmans.jstratego2021;

import com.baartmans.jstratego2021.gamelogic.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private GameService gs = new GameService();

    @CrossOrigin
    @GetMapping("/")
    public String getSlash() {
        return "OK";
    }

    @CrossOrigin
    @GetMapping("/version")
    public String getVersion() {
        return "JStratego2021 version 0.6";
    }

    @CrossOrigin
    @GetMapping("/newgame")
    public GameState newGame() {
        return  gs.newGame();
    }

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
}