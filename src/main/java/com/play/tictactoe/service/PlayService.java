package com.play.tictactoe.service;

import com.play.tictactoe.dto.MarkDto;
import com.play.tictactoe.model.Game;

public interface PlayService {
    void mark(Game game, MarkDto markDto);

    boolean checkWinner();
}