package com.mccullough.highscore.dao;

import com.mccullough.highscore.exception.DaoException;
import com.mccullough.highscore.model.UserScore;
import org.apache.catalina.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserScoreDao implements UserScoreDao{

    private final JdbcTemplate jdbcTemplate;

    private final String USER_SCORE_BASE_SQL = "SELECT id, player, score\n" +
            "\tFROM public.user_scores ";

    public JdbcUserScoreDao (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<UserScore> getUserScores(int limit) {

        List<UserScore> userScores = new ArrayList<>();
        String sql = USER_SCORE_BASE_SQL + "ORDER BY score DESC LIMIT ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, limit);

            while (results.next()) {
                UserScore userScore = mapRowToUserScore(results);
                userScores.add(userScore);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Cannot connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return userScores;
    }

    @Override
    public List<UserScore> getUserScoresToday(int limit) {
        List<UserScore> userScores = new ArrayList<>();

        String sql = USER_SCORE_BASE_SQL +
                "WHERE date = CURRENT_DATE\n" +
                "ORDER BY score DESC\n" +
                "LIMIT ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, limit);

            while (results.next()){
                UserScore userScore = mapRowToUserScore(results);
                userScores.add(userScore);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Cannot connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return userScores;
    }

    @Override
    public UserScore getUserScoreById(int id) {
        UserScore userScore = null;

        String sql = USER_SCORE_BASE_SQL + "WHERE id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

            if (results.next()){
                userScore = mapRowToUserScore(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Cannot connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return userScore;

    }


    @Override
    public UserScore createUserScore(UserScore newUserScore) {
        UserScore userScore = null;
        String sql = "INSERT INTO public.user_scores(\n" +
                "\tplayer, score)\n" +
                "\tVALUES (?, ?) RETURNING id;";

        try {
            int newId = jdbcTemplate.queryForObject(sql, int.class, newUserScore.getUserName(), newUserScore.getScore());

            userScore = getUserScoreById(newId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return userScore;
    }

    private UserScore mapRowToUserScore(SqlRowSet results) {
        UserScore userScore = new UserScore();
        userScore.setId(results.getInt("id"));
        userScore.setUserName(results.getString("player"));
        userScore.setScore(results.getInt("score"));
        return userScore;
    }
}
