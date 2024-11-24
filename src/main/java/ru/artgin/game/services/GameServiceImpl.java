package ru.artgin.game.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artgin.game.enums.Move;
import ru.artgin.game.enums.Result;
import ru.artgin.game.models.Statistic;


import java.util.Arrays;
import java.util.Random;

/**
 *It is the class, which implements the logic of choosing the computer's response in game.
 *
 * The random way simply made random answer.
 * Another implementation of the logic of the computer game is based on the idea
 * that when a person loses, he changes answer in next round. If he wins, he does not change the answer.
 *
 * Logic: if computer loses, then next time it should throw out an item that was not in the last round.
 * If computer win, then in the next round you must throw out the item that the person showed in the last round.
 */

@Service("gameServiceImpl")
public class GameServiceImpl implements GameService{

    StatisticsService statisticsService;

    @Autowired
    public GameServiceImpl( StatisticsService statisticsService){
        this.statisticsService = statisticsService;
    }

    @Override
    public Move getRandomMove(){
        Move[] moveArr = Move.values();
        Random random = new Random();
        int num = random.nextInt(Move.values().length);
        return moveArr[num];
    }

    @Override
    public Move getStrategyMove() {
        if(statisticsService.isEmpty()){
            Statistic stat = statisticsService.getLastStatistic();
            Move userMove = stat.getUserMove();
            Move compMove = stat.getCompMove();
            Result result = stat.getResult();

            if(result.equals(Result.LOSS)){
                return userMove;
            }else if(result.equals(Result.WIN)){
                Move[] moveArr = Move.values();
                return Arrays.stream(moveArr)
                        .filter(m -> !(m.equals(userMove)))
                        .filter(m -> !(m.equals(compMove)))
                        .findFirst().get();
            }else{
                return getRandomMove();
            }
        }else{
            return getRandomMove();
        }
    }

    @Override
    public Result chooseWinner(Move move, Move compMove) {
        if(compMove.equals(move))
            return Result.DRAW;
        switch(move){
            case PIPER:
                if(compMove.equals(Move.STONE)){
                    return Result.WIN;
                }else{
                    return Result.LOSS;
                }
            case STONE:
                if(compMove.equals(Move.SCISSORS)){
                    return Result.WIN;
                }else{
                    return Result.LOSS;
                }
            case SCISSORS:
                if(compMove.equals(Move.PIPER)){
                    return Result.WIN;
                }else{
                    return Result.LOSS;
                }
        }
    return Result.DRAW;
    }


}
