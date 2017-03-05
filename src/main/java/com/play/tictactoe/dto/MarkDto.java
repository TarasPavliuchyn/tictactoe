package com.play.tictactoe.dto;

import com.play.tictactoe.model.Player;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class MarkDto {
    @NotNull(message = "cellId can't be null!")
    private Integer cellId;
    @NotNull(message = "player can't be null!")
    private Player player;
}
