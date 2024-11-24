package ru.artgin.game.services;

import ru.artgin.game.enums.Move;
import ru.artgin.game.enums.Result;

public interface GameService {
    public Move getRandomMove();
    public Move getStrategyMove();
    public Result chooseWinner(Move move, Move compMove);



}
