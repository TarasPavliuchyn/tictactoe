package com.play.tictactoe.dao.impl;

import com.play.tictactoe.dao.GameDao;
import com.play.tictactoe.model.Game;
import com.play.tictactoe.model.Mark;
import com.play.tictactoe.model.Player;
import com.play.tictactoe.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GameDaoImpl implements GameDao {

    private static final String INSERT_NEW_GAME = "INSERT INTO game(game_name,status,game_created_date) VALUES(?,?,?)";
    private static final String UPDATE_GAME_STATUS = "UPDATE game SET status = ? WHERE game_id = ?";
    private static final String SELECT_ALL_GAMES = "SELECT game_id, game_name, status, game_created_date, null as mark_id FROM game ORDER BY game_created_date DESC";
    private static final String SELECT_GAME_BY_ID = "SELECT game.game_id, game_name, status, game_created_date, mark_id, cell_number, player, mark_created_date " +
            "FROM game LEFT JOIN mark ON game.game_id = mark.game_id " +
            "WHERE game.game_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Game save(Game game) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_NEW_GAME, new String[]{"game_id"});
                    ps.setString(1, game.getGameName());
                    ps.setString(2, game.getStatus().name());
                    ps.setTimestamp(3, new Timestamp(game.getGameCreatedDate().getTime()));
                    return ps;
                },
                keyHolder);
        game.setGameId(keyHolder.getKey().longValue());
        return game;
    }

    @Override
    public void update(Game game) {
        jdbcTemplate.update(UPDATE_GAME_STATUS, new Object[]{game.getStatus().name(), game.getGameId()});
    }

    @Override
    public List<Game> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GAMES, new GameResultSetExtractor());
    }

    @Override
    public Game findById(Long id) {
        return jdbcTemplate.query(SELECT_GAME_BY_ID, new Object[]{id}, new GameResultSetExtractor())
                .stream()
                .findFirst()
                .orElse(null);
    }

    private static class GameResultSetExtractor implements ResultSetExtractor<List<Game>> {
        @Override
        public List<Game> extractData(ResultSet resultSet) throws SQLException {
            Map<Long, Game> gameAndMarks = new LinkedHashMap<>();
            while (resultSet.next()) {
                long gameId = resultSet.getLong("game_id");
                Game game = gameAndMarks.get(gameId);
                if (game == null) {
                    game = new Game();
                    game.setGameId(gameId);
                    game.setGameName(resultSet.getString("game_name"));
                    game.setStatus(Status.valueOf(resultSet.getString("status")));
                    game.setGameCreatedDate(resultSet.getTimestamp("game_created_date"));
                    gameAndMarks.put(gameId, game);
                }
                Integer markId = resultSet.getObject("mark_id", Integer.class);
                if (markId != null && markId > 0) {
                    Mark mark = new Mark();
                    mark.setMarkId(markId);
                    mark.setGameId(gameId);
                    mark.setCellNumber(resultSet.getInt("cell_number"));
                    Player player = Player.valueOf(resultSet.getString("player"));
                    mark.setPlayer(player);
                    mark.setMarkCreatedDate(resultSet.getTimestamp("mark_created_date"));
                    game.setCurrentPlayer(player);
                    game.getMarks().put(mark.getCellNumber(), mark);
                }
            }
            return new ArrayList<>(gameAndMarks.values());
        }
    }
}