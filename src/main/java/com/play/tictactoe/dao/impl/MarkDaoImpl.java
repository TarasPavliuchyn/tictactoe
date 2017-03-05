package com.play.tictactoe.dao.impl;

import com.play.tictactoe.dao.MarkDao;
import com.play.tictactoe.model.Mark;
import com.play.tictactoe.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MarkDaoImpl implements MarkDao {

    private static final String SELECT_MARKS_BY_GAME_ID = "SELECT mark_id, game_id, cell_number, player, mark_created_date " +
            "FROM mark WHERE game_id = ? ORDER BY mark_created_date";
    private static final String INSERT_NEW_MARK = "INSERT INTO mark(game_id,cell_number,player,mark_created_date) values(?,?,?,?) ";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void save(Mark mark) {
        Object[] params = {mark.getGameId(), mark.getCellNumber(), mark.getPlayer().name(), mark.getMarkCreatedDate()};
        jdbcTemplate.update(INSERT_NEW_MARK, params);
    }

    @Override
    public List<Mark> findAllByGameId(Long gameId) {
        return jdbcTemplate.query(SELECT_MARKS_BY_GAME_ID, new Object[]{gameId}, new GameRowMapper());
    }

    static class GameRowMapper implements RowMapper<Mark> {
        @Override
        public Mark mapRow(ResultSet resultSet, int i) throws SQLException {
            Mark mark = new Mark();
            mark.setMarkId(resultSet.getInt("mark_id"));
            mark.setGameId(resultSet.getLong("game_id"));
            mark.setCellNumber(resultSet.getInt("cell_number"));
            mark.setPlayer(Player.valueOf(resultSet.getString("player")));
            mark.setMarkCreatedDate(resultSet.getDate("mark_created_date"));
            return mark;
        }
    }
}