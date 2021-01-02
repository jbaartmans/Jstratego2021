package com.baartmans.jstratego2021;

import com.baartmans.jstratego2021.gamelogic.GameState;
import com.baartmans.jstratego2021.gamelogic.MoveResponse;
import com.baartmans.jstratego2021.gamelogic.Pawn;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameTestController {

    private GameTestService gst = new GameTestService();

    @CrossOrigin
    @GetMapping("/test/newgame")
    public GameState newGame() {
        return  gst.newTestGame();
    }

}