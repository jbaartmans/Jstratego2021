package com.baartmans.jstratego2021;

import com.baartmans.jstratego2021.gamelogic.GameState;
import com.baartmans.jstratego2021.gamelogic.MoveResponse;
import com.baartmans.jstratego2021.gamelogic.Pawn;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private GameService gs = new GameService();

    @CrossOrigin
    @GetMapping("/version")
    public String getVersion() {
        return  "JStratego2021 version 0.1 ";
    }

    @CrossOrigin
    @GetMapping("/newgame")
    public GameState newGame() {
        return  gs.newGame();
    }

    @CrossOrigin
    @RequestMapping(value = "/domove", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GameState doMove(@RequestBody GameState gamestate) {
        //GameService a = new GameService(); //?? waarom weer een nieuwe instantie?

        System.out.println("DoMove wordt aangeroepen");
        for(Pawn i : gamestate.getTeam2()){
            System.out.println(i.getType() + " " + i.getLocation()[0] + ":" + i.getLocation()[1] );
        }
        //return  gs.doMove();

        return gamestate;
    }

//    @GetMapping("/error")
//    public String handleError(HttpServletRequest request) {
//        return "error";
//    }

}