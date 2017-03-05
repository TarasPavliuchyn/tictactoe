package com.play.tictactoe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Mark {
    private Integer markId;
    private Long gameId;
    private Integer cellNumber;
    private Player player;
    private Date markCreatedDate;
}
