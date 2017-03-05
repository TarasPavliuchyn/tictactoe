package com.play.tictactoe.controller;

import com.play.tictactoe.dto.MarkDto;
import com.play.tictactoe.model.Game;
import com.play.tictactoe.model.Player;
import com.play.tictactoe.model.Status;
import com.play.tictactoe.service.GameService;
import com.play.tictactoe.service.PlayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
public class PlayController {

    public static final int CELL_COUNT = 9;
    public static final int MIN_MARK_COUNT_FOR_WIN = 5;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayService playService;

    @Autowired
    private HttpSession httpSession;


    @PostMapping("/mark")
    public ResponseEntity<?> doMark(@Valid @RequestBody MarkDto markDto) {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        log.info("User '{}' mark cell {} in game {}", markDto.getPlayer(), markDto.getCellId(), gameId);

        Game currentGame = gameService.findById(gameId);
        if (currentGame.getStatus() != Status.IN_PROGRESS || currentGame.getMarks().get(markDto.getCellId()) != null) {
            return ResponseEntity.badRequest().body(currentGame);
        }
        playService.mark(currentGame, markDto);
        int markCount = currentGame.getMarks().size();
        if (markCount >= MIN_MARK_COUNT_FOR_WIN) {
            if (playService.checkWinner()) {
                currentGame.setStatus(markDto.getPlayer() == Player.O ? Status.O_WON : Status.X_WON);
                gameService.finishGame(currentGame);
                log.info("User '{}' won in game {}", markDto.getPlayer(), gameId);
            } else if (markCount == CELL_COUNT) {
                currentGame.setStatus(Status.DRAW);
                gameService.finishGame(currentGame);
                log.info("Draw in game {}", gameId);
            }
        }
        return ResponseEntity.ok(currentGame);
    }

    @PostMapping("/find")
    public ResponseEntity<?> gameById(@RequestBody Long gameId) {
        Game game = gameService.findById(gameId);
        return ResponseEntity.ok(game);
    }

}