package com.play.tictactoe.service.impl;

import com.play.tictactoe.dao.GameDao;
import com.play.tictactoe.dao.MarkDao;
import com.play.tictactoe.dto.MarkDto;
import com.play.tictactoe.model.Game;
import com.play.tictactoe.model.Mark;
import com.play.tictactoe.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

import static com.play.tictactoe.controller.PlayController.CELL_COUNT;


@Service
public class PlayServiceImpl implements PlayService {

    @Autowired
    private MarkDao markDao;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private HttpSession httpSession;

    @Override
    public void mark(Game game, MarkDto markDto) {
        Mark mark = new Mark();
        mark.setGameId(game.getGameId());
        mark.setPlayer(markDto.getPlayer());
        mark.setCellNumber(markDto.getCellId());
        mark.setMarkCreatedDate(new Date());
        game.addMark(mark);
        markDao.save(mark);
    }

    @Override
    public boolean checkWinner() {
        Game game = gameDao.findById((Long) httpSession.getAttribute("gameId"));
        Map<Integer, Mark> marks = game.getMarks();
        return checkRows(marks) || checkColumns(marks) || checkLeftDiagonal(marks) || checkRightDiagonal(marks);
    }

    private boolean checkColumns(Map<Integer, Mark> marks) {
        int cell1 = 0;
        int cell2 = 3;
        int cell3 = 6;

        while (cell3 < CELL_COUNT) {
            if (checkWinner(marks.get(cell1), marks.get(cell2), marks.get(cell3))) {
                return true;
            }
            cell1 += 1;
            cell2 += 1;
            cell3 += 1;
        }
        return false;
    }

    private boolean checkRows(Map<Integer, Mark> marks) {
        int cell1 = 0;
        int cell2 = 1;
        int cell3 = 2;
        while (cell3 < CELL_COUNT) {
            if (checkWinner(marks.get(cell1), marks.get(cell2), marks.get(cell3))) {
                return true;
            }
            cell1 += 3;
            cell2 += 3;
            cell3 += 3;
        }
        return false;
    }

    private boolean checkRightDiagonal(Map<Integer, Mark> marks) {
        int cell1 = 0;
        int cell2 = 4;
        int cell3 = 8;
        if (checkWinner(marks.get(cell1), marks.get(cell2), marks.get(cell3))) {
            return true;
        }
        return false;
    }

    private boolean checkLeftDiagonal(Map<Integer, Mark> marks) {
        int cell1 = 2;
        int cell2 = 4;
        int cell3 = 6;
        if (checkWinner(marks.get(cell1), marks.get(cell2), marks.get(cell3))) {
            return true;
        }
        return false;
    }

    private boolean checkWinner(Mark cell1, Mark cell2, Mark cell3) {
        if (cell1 != null && cell2 != null && cell3 != null
                && cell1.getPlayer().equals(cell2.getPlayer()) && cell2.getPlayer().equals(cell3.getPlayer())) {
            return true;
        } else {
            return false;
        }
    }
}
