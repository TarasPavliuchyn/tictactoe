package com.play.tictactoe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Game {
    private Long gameId;
    private String gameName;
    private Status status;
    private Date gameCreatedDate;
    private Player currentPlayer;
    private Map<Integer, Mark> marks = new HashMap<>();

    public Game() {
        this.gameCreatedDate = new Date();
    }

    public void addMark(Mark mark) {
        marks.put(mark.getCellNumber(), mark);
    }
}
