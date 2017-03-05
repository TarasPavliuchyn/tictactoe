package com.play.tictactoe.service;

import com.play.tictactoe.model.Game;

import java.util.List;

public interface GameService {

    Game createNewGame(String gameName);

    void finishGame(Game game);

    List<Game> findAll();

    Game findById(Long id);
}
