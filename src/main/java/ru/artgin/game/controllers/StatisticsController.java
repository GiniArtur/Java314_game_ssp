package ru.artgin.game.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.artgin.game.enums.Move;
import ru.artgin.game.models.Statistic;
import ru.artgin.game.services.StatisticsService;
import ru.artgin.game.services.UserService;

import java.util.List;

/**
 *It is the controller class, which implements the viewing statistics.
 *
 * Statistic will be written with tne name of current nickname with status Gamer.
 * If there are no Gamers, only nicknames with status Guests, game  will not start
 * and the statistics will not be recorded
 *
 * User can look at general all user's statistic, or take statistic by certain user.
 * Also, user can see general and certain user's calculated metrics: amount of games, amount of
 * wins,losses and draws, move, which was used more often.
 *
 * REST API:
 *  GET /statistics - get all statistics for all users
 *  GET /statistics/{user} - get statistics for certain user
 *  GET /statistics/count - get statistical metrics counted for  all users
 *  GET /statistics/count/name - get statistic counted for certain user
 */

@RestController
@RequestMapping(value ="/statistics")
public class StatisticsController {
    private StatisticsService statisticsService;
    private UserService userService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, UserService userService) {
        this.statisticsService = statisticsService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<Statistic> getAllStatistics(){
        if(statisticsService.isEmpty()) {
            return statisticsService.getStatistics();
        }else{
            return null;
        }
    }
    @GetMapping("/{name}")
    public List<Statistic> getStatisticsByName(@PathVariable String name){
        if(userService.existName(name)) {
            return statisticsService.getStatisticByName(name);
        } else{
            return null;
            }
    }

    @GetMapping("count/")
    public String getCountedStatistic(){
        if(statisticsService.isEmpty()) {
            int games = statisticsService.countGames();
            int wins = statisticsService.countWin();
            int losses = statisticsService.countLosses();
            int draws = statisticsService.countDraws();
            Move move = statisticsService.oftenUserMove();

            return "Total games: " + games + "\n" +
                    "Total wins: " + wins + "\n" +
                    "Total losses: " + losses + "\n" +
                    "Total draws: " + draws + "\n" +
                    "Often move: " + move + "\n";
        }else{
            return "There is no statistic yet!";
        }
    }

    @GetMapping("count/{name}")
    public String getCountedStatistic(@PathVariable String name){
         if(userService.existName(name)) {
             int games = statisticsService.countGamesByName(name);
             int wins = statisticsService.countWinByName(name);
             int losses = statisticsService.countLossesByName(name);
             Move move = statisticsService.oftenUserMoveByName(name);
             int draws = statisticsService.countDrawsByName(name);

             return "Total games: " + games + "\n" +
                     "Total wins: " + wins + "\n" +
                     "Total losses: " + losses + "\n" +
                     "Total draws: " + draws + "\n" +
                     "Often move: " + move + "\n";
         }else{
             return null;
         }
    }



}
