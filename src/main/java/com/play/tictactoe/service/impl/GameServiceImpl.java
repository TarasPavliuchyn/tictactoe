package com.play.tictactoe.service.impl;

import com.play.tictactoe.dao.GameDao;
import com.play.tictactoe.model.Game;
import com.play.tictactoe.model.Status;
import com.play.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDao gameDao;

    @Override
    public Game createNewGame(String gameName) {
        Game newGame = new Game();
        newGame.setGameName(gameName);
        newGame.setStatus(Status.IN_PROGRESS);
        return gameDao.save(newGame);
    }

    @Override
    public void finishGame(Game game) {
         gameDao.update(game);
    }

    @Override
    public List<Game> findAll() {
        return gameDao.findAll();
    }

    @Override
    public Game findById(Long id) {
        return gameDao.findById(id);
    }
}
