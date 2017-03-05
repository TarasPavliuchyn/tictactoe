package com.play.tictactoe.dao;

import com.play.tictactoe.model.Game;

public interface GameDao extends CrudDao<Game, Long> {
    default void remove(Game model) {
        throw new UnsupportedOperationException("Not supported yet");
    }
}