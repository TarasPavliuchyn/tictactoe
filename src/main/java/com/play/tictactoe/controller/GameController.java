package com.play.tictactoe.controller;

import com.play.tictactoe.model.Game;
import com.play.tictactoe.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private HttpSession httpSession;

    @RequestMapping
    public String getAllGames(Model model) {
        log.info("Get all games");
        List<Game> games = gameService.findAll();
        model.addAttribute("games", games);
        return "index";
    }

    @RequestMapping("/game")
    public String getGameById(@RequestParam("gameId") Long gameId, Model model) {
        log.info("Get the game with id {}", gameId);
        Game game = gameService.findById(gameId);
        if (game == null) {
            throw new GameNotFoundException(String.format("Game with id %d does not exist", gameId));
        }
        httpSession.setAttribute("gameId", game.getGameId());
        model.addAttribute("game", game);
        return "game";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createNewGame(@RequestParam("gameName") String gameName, Model model) {
        log.info("Create new game");
        Game game = gameService.createNewGame(gameName);
        model.addAttribute("game", game);
        httpSession.setAttribute("gameId", game.getGameId());
        return "game";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class GameNotFoundException extends RuntimeException {
        public GameNotFoundException(String message) {
            super(message);
        }
    }

}