package ru.artgin.game.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artgin.game.enums.Type;
import ru.artgin.game.enums.Move;
import ru.artgin.game.enums.Result;
import ru.artgin.game.models.Statistic;
import ru.artgin.game.models.User;
import ru.artgin.game.repositoty.StatisticsRepository;
import ru.artgin.game.repositoty.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


/**
 *It is the class, which is which implements the logic of using statistic's information.
 * In this class methods of getting statistics and calculating statistical metrics are implemented.
 * Also, there is a method of add statistic information to the table.
 */


@Service
public class StatisticsServiceImpl implements StatisticsService {

    private StatisticsRepository statisticsRepository;
    private UserRepository userRepository;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, UserRepository userRepository){
        this.statisticsRepository = statisticsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Statistic addStatistics(User user, Result res, Move compMove, Move userMove, Type type) {
        Statistic stat = new Statistic();
        stat.setResult(res);
        stat.setCompMove(compMove);
        stat.setUserMove(userMove);
        stat.setUser(user);
        stat.setType(type);
        statisticsRepository.save(stat);
    return stat;
    }

    @Override
    public List<Statistic> getStatistics() {
        return statisticsRepository.findAll()
                .stream().collect(Collectors.toList());
    }

    @Override
    public Statistic getLastStatistic() {
       return statisticsRepository.findTopByOrderByIdDesc();
    }

    @Override
    public List<Statistic> getStatisticByName(String name) {
        return statisticsRepository
                .findByUserId(userRepository.findUserByName(name).getId());
    }

    @Override
    public boolean isEmpty() {
        return statisticsRepository.existsBy();
    }

    @Override
    public int countWin() {
        return (int) statisticsRepository.findAll()
                .stream().filter(s -> s.getResult().equals(Result.WIN))
                .count();
    }

    @Override
    public int countWinByName(String name) {
        return (int) statisticsRepository
                .findByUserId(userRepository.findUserByName(name).getId())
                .stream().filter(s -> s.getResult().equals(Result.WIN))
                .count();
    }

    @Override
    public int countLosses() {
        return (int) statisticsRepository.findAll()
                .stream().filter(s -> s.getResult().equals(Result.LOSS))
                .count();
    }

    @Override
    public int countLossesByName(String name) {
        return (int) statisticsRepository
                .findByUserId(userRepository.findUserByName(name).getId())
                .stream().filter(s -> s.getResult().equals(Result.LOSS))
                .count();
    }

    @Override
    public int countDraws() {
        return (int) statisticsRepository.findAll()
                .stream().filter(s -> s.getResult().equals(Result.DRAW))
                .count();
    }

    @Override
    public int countDrawsByName(String name) {
        return (int) statisticsRepository
                .findByUserId(userRepository.findUserByName(name).getId())
                .stream().filter(s -> s.getResult().equals(Result.DRAW))
                .count();
    }



    @Override
    public int countGames() {
        return (int) statisticsRepository.findAll()
                .stream().count();
    }

    @Override
    public int countGamesByName(String name) {
        return (int) statisticsRepository
                .findByUserId(userRepository.findUserByName(name).getId())
                .stream().count();
    }

    @Override
    public Move oftenUserMoveByName(String name) {
        int stoneCount =(int) statisticsRepository
                .findByUserId(userRepository.findUserByName(name).getId())
                .stream()
                .filter(s -> s.getUserMove().equals(Move.STONE)).count();
        int piperCount =(int) statisticsRepository
                .findByUserId(userRepository.findUserByName(name).getId())
                .stream()
                .filter(s -> s.getUserMove().equals(Move.PIPER)).count();
        int scissorsCount =(int) statisticsRepository
                .findByUserId(userRepository.findUserByName(name).getId())
                .stream()
                .filter(s -> s.getUserMove().equals(Move.SCISSORS)).count();

        if(stoneCount >= piperCount){
            if(stoneCount >= scissorsCount){
                return Move.STONE;
            }else{
                return Move.SCISSORS;
            }
        }else if(piperCount >= scissorsCount){
            return Move.PIPER;
        }else{
            return Move.SCISSORS;
        }
    }
    @Override
    public Move oftenUserMove() {
        int stoneCount =(int) statisticsRepository
                .findAll()
                .stream()
                .filter(s -> s.getUserMove().equals(Move.STONE)).count();
        int piperCount =(int) statisticsRepository
                .findAll()
                .stream()
                .filter(s -> s.getUserMove().equals(Move.PIPER)).count();
        int scissorsCount =(int) statisticsRepository
                .findAll()
                .stream()
                .filter(s -> s.getUserMove().equals(Move.SCISSORS)).count();

        if(stoneCount >= piperCount){
            if(stoneCount >= scissorsCount){
                return Move.STONE;
            }else{
                return Move.SCISSORS;
            }
        }else if(piperCount >= scissorsCount){
            return Move.PIPER;
        }else{
            return Move.SCISSORS;
        }

    }


}
