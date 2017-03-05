package com.play.tictactoe.dao;

import com.play.tictactoe.model.Mark;

import java.util.List;

public interface MarkDao {

    void save(Mark model);

    List<Mark> findAllByGameId(Long gameId);
}