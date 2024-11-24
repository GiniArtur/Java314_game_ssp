package ru.artgin.game.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.artgin.game.enums.Type;
import ru.artgin.game.enums.Move;
import ru.artgin.game.enums.Result;
import ru.artgin.game.enums.Status;
import ru.artgin.game.services.GameService;
import ru.artgin.game.services.StatisticsService;
import ru.artgin.game.services.UserService;

/**
 *It is the controller class, which implements the game in two ways.
 *
 * Only user with status Gamer can play (get the result by game url). To have this status user have to start the game.
 * If there are no Gamers among nicknames, game won't play, nothing happens.
 *
 *
 * At tne game, user don't need to indicate the nickname, statistic was written to the current gamer name.
 * At the game user have to indicate the type of the game: random or strategy behavior of computer.
 * As the result of request user get thr string with the information about the game.
 *
 * User can pass answers as much as he wants, while nickname has status Guest.
 *
 * REST API:
 *  GET /game/{type}/{move}  - get computer's answer in game.
 *                            type - type of the logic of computer's playing (random, strategy).
 *                            move - one of three possible move (stone, piper, scissors
 */

@RestController
@RequestMapping(value ="/game")
public class GameController {

    private GameService gameService;
    private StatisticsService statisticsService;
    private UserService userService;

    @Autowired
    public  GameController(GameService gameService, StatisticsService statisticsService,
                           UserService userService) {
        this.gameService = gameService;
        this.statisticsService = statisticsService;
        this.userService = userService;
    }

    @GetMapping("{type}/{move}")
    public String gaming(@PathVariable String type, @PathVariable String move){

        if(userService.existsStatus(Status.GAMER)) {
            Type chosenType = Type.valueOf(type.toUpperCase());
            Move compMove;

            if(chosenType.equals(Type.STRATEGY)){
                compMove = gameService.getStrategyMove();
            }else{
                compMove = gameService.getRandomMove();
            }

            Move userMove = Move.valueOf(move.toUpperCase());
            Result result = gameService.chooseWinner(userMove, compMove);
            statisticsService.addStatistics(userService.getUserByStatus(Status.GAMER),
                                                result, compMove, userMove, chosenType);

            return "Your move was: " + move.toUpperCase() + "\n"+
                    "Computer move was: " + compMove +"\n" +
                    "Result: " + result;

        }else {
            return "You didn't start the game with any nickname";
        }

    }





}
